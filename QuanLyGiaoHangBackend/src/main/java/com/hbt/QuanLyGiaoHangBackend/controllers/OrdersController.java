package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.config.VnpayConfig;
import com.hbt.QuanLyGiaoHangBackend.dto.request.PaymentRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrderDetailResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrdersResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.UserResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.*;
import com.hbt.QuanLyGiaoHangBackend.repositories.ProductRepository;
import com.hbt.QuanLyGiaoHangBackend.services.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequestMapping("/api/")
@RestController
public class OrdersController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProofService proofService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DistrictsService districtsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VnpayConfig Config;

    @Autowired
    private DauGiaService dauGiaService;

    @Autowired
    private CommonService commonService;


    @PostMapping(path = "customer/orders/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<List<?>> orders(@RequestParam Map<String, String> params, Authentication authentication) {

        List<?> res = this.productService.getProducts(params);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("admin/allOrders/")
    public List<Product> getAllProducts() {
        return productService.getAllProductsForAdmin();
    }


    @PostAuthorize("returnObject.user.username == authentication.name or hasRole('ADMIN') or @dauGiaRepository.existsByProductIdAndShipperWithSelected(#id, authentication.name)")
    @GetMapping("customer/getOrder/{orderId}/")
    public Product getProductByIDForUser(@PathVariable("orderId") Long id, Authentication authentication){
        return this.productService.getProductByIdForUser(id,authentication.getName());

    }

    @PreAuthorize("@dauGiaRepository.existsByProductIdAndShipperWithSelected(#productId, authentication.name)")
    @PostMapping("shipper/addProof/{productId}")
    public ResponseEntity<String> addProof(@PathVariable("productId") long productId, @RequestParam MultipartFile image
    ) throws MessagingException {
        Proof proof = this.proofService.saveOrUpdateProof(productId,image);
        emailService.sendProofFromShipper(proof.getProduct());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("shipper/getOrder/{orderId}/")
    public OrderDetailResponse getProductShipper(@PathVariable("orderId") Long id){
        OrderDetailResponse o = this.productService.getProductById(id);
        return o;
    }

    @PostMapping("customer/pay/")
    public String payment(@RequestBody PaymentRequest request){
        DauGia dauGiaSelected = request.getDauGiaSelected();
        double totalPrice = dauGiaSelected.getPrice();
        long voucherId = request.getVoucherId();

        if (voucherId > 0) {
            UserHaveVoucher uv = voucherService.getUserVoucherById(voucherId);
            Voucher v = uv.getVoucher();
            double value = v.getValue();
            totalPrice -= v.getMeasurement().equals("%") ? totalPrice * value / 100.0 : value;
        }

        String locate = "vn";
        String vnp_OrderInfo = String.valueOf(dauGiaSelected.getDauGiaPK());
        String urlReturn = Config.vnp_ReturnUrl + dauGiaSelected.getDauGiaPK().getProductId();
        String vnpayUrl = this.productService.createOrder((int)totalPrice ,
                "Payment for order " + vnp_OrderInfo, urlReturn, locate);

        return vnpayUrl;
    }

    @PostMapping("customer/vnpay-return/")
    public String paymentReturn( @RequestBody PaymentRequest request
    ) {

        try {
            String toSelectedEmail = request.getDauGiaSelected().getShipper().getUser().getEmail();
            OrderDetailResponse p = productService.getProductById(request.getDauGiaSelected().getDauGiaPK().getProductId());

            // Xử lý trả về từ VNPAY, gửi email thông báo
            emailService.sendRefuseEmail(request.getDauGiaSet(), p);
            emailService.sendEmailAlertSuccess(toSelectedEmail, p);

            //cập nhật csdl nếu thành công

            //cập nhật voucher của user
            long voucherId = request.getVoucherId();
            
            if (voucherId > 0) {
                UserHaveVoucher uv = voucherService.getUserVoucherById(voucherId);
                int newQuantity = uv.getQuantity()-1;

                if(newQuantity==0)
                    voucherService.deleteUserVoucherById(voucherId);
                else {uv.setQuantity(newQuantity);
                    voucherService.updateUserVoucher(uv);
                }
            }

            //cập nhật ngày thanh toán đơn hàng
            long productID = request.getDauGiaSelected().getDauGiaPK().getProductId();
            Product product = this.productRepository.findById(productID).orElse(null);
            productService.updateProduct(product);

            //cập nhật trạng thái đấu giá
            DauGia d =request.getDauGiaSelected();
            System.out.println(d);
            d.setSelected(true);
            dauGiaService.updateDauGia(d);

            //xóa các đấu giá không được chọn
            dauGiaService.deleteDauGia(product);


            return "Payment Success";
        }
        catch (Exception e){System.out.println(e);}
        return "Payment Failed";
    }

    @PostMapping("customer/addOrder/")
    public ResponseEntity<String> addOrder( @RequestParam Map<String, String> params,@RequestParam MultipartFile image
    ) {
        this.productService.addOrder(params,image);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }



}
