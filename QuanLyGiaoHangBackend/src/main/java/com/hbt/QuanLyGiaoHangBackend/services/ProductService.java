package com.hbt.QuanLyGiaoHangBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hbt.QuanLyGiaoHangBackend.Specification.DauGiaSpecifications;
import com.hbt.QuanLyGiaoHangBackend.Specification.ProductSpecifications;
import com.hbt.QuanLyGiaoHangBackend.config.VnpayConfig;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrderDetailResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrdersResponse;
import com.hbt.QuanLyGiaoHangBackend.exception.AppException;
import com.hbt.QuanLyGiaoHangBackend.exception.ErrorCode;
import com.hbt.QuanLyGiaoHangBackend.mapper.DauGiaMapper;
import com.hbt.QuanLyGiaoHangBackend.mapper.OrderMapper;
import com.hbt.QuanLyGiaoHangBackend.pojo.*;
import com.hbt.QuanLyGiaoHangBackend.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DauGiaMapper dauGiaMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VnpayConfig vnpayConfig;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private DauGiaRepository dauGiaRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Product getProductByIdForUser(long productId, String username) {
        Product product = productRepository.findById(productId).orElse(null);
        return product;
    }

    public OrderDetailResponse getProductById(long id) {
        Product p = this.productRepository.findById(id).orElse(null);
        return orderMapper.toOrderDetailResponse(p);
    }

    public boolean addOrder(Map<String, String> params, MultipartFile image) {
        Districts from = this.districtsRepository.findById(Integer.parseInt(params.get("senderDistrict"))).orElse(null);
        Districts to = this.districtsRepository.findById(Integer.parseInt(params.get("receiverDistrict"))).orElse(null);
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User u = this.userRepository.findByUsername(username).orElse(null);
        Product p = new Product();
        String cateList = params.get("category");
        if(!cateList.isEmpty() && cateList!=null){
            List<Integer> intList = commonService.convertStringToList(params.get("category"), Integer::parseInt);
            List<Category> categoryList = this.categoryRepository.findAllById(intList);
            p.setCategorySet(commonService.convertToSet(categoryList));
        }

        com.hbt.QuanLyGiaoHangBackend.pojo.Service s = serviceRepository.findById(Integer.parseInt(params.get("service"))).orElse(null);

        if (image != null && !image.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(image.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                p.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        p.setCreatedDate();
        p.setServiceId(s);
        p.setName(params.get("productName"));
        p.setUser(u);
        p.setDeliveryAddress(params.get("senderAddress"));
        p.setReceiverAddress(params.get("receiverAddress"));
        p.setFromDistrictId(from);
        p.setToDistrictId(to);
        p.setQuantity(Integer.parseInt(params.get("quantity")));
        p.setNote(params.get("note"));
        p.setSenderPhone(params.get("senderPhone"));
        p.setReceiverPhone(params.get("receiverPhone"));
        p.setSenderName(params.get("senderFullname"));
        p.setReceiverName(params.get("receiverFullname"));
        this.productRepository.save(p);
        return true;
    }

    @Value("${PAGE_SIZE}")
    private int pageSize;

    public List<?> getProducts(@RequestParam Map<String, String> params) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        int page = 0;
        if (params != null) {
            String pageParam = params.get("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam) - 1; // JPA page index starts from 0
            }
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        User currentUser = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Product> products =null;
        Specification<DauGia> specDauGia = Specification.where(null);
        Specification<Product> specProduct = Specification.where(null);



        if (params != null) {
            String serviceParam = params.get("services");
            if (serviceParam != null && !serviceParam.isEmpty()) {
                List<Integer> intList = commonService.convertStringToList(serviceParam, Integer::parseInt);
                specProduct= specProduct.and(ProductSpecifications.productByService(intList));
            }

            String fromParam = params.get("from");
            String toParam = params.get("to");
            if (fromParam != null && !fromParam.isEmpty()) {
                specProduct= specProduct.and(ProductSpecifications.productByProvince(Integer.parseInt(fromParam),true));
            }
            if (toParam != null && !toParam.isEmpty()) {
                specProduct= specProduct.and(ProductSpecifications.productByProvince(Integer.parseInt(toParam), false));
            }

            String statusParam = params.get("status");
            if (statusParam != null && !statusParam.isEmpty()) {
                switch (statusParam){
                    case "owner":
                        boolean isPay = Boolean.parseBoolean(params.get("isPay"));

                        specProduct= Specification
                                .where(ProductSpecifications.findByUser(currentUser))
                                .and(ProductSpecifications.findByPaymentDate(isPay));
                        products = productRepository.findAll(specProduct, pageable).getContent();
                        return orderMapper.toOrdersResponseList(products);
                    case "NotYetPriced":
                        specProduct = specProduct.and(ProductSpecifications.productByShipper(currentUser, params));
                        products = productRepository.findAll(specProduct, pageable).getContent();
                        return orderMapper.toOrdersResponseList(products);
                    case "AwaitingSelection":
                        specDauGia = specDauGia.and(DauGiaSpecifications.dauGiaBySelected(false))
                                .and(DauGiaSpecifications.dauGiaByShipper(currentUser.getShipper()));
                        break;
                    case "Selected":
                        specDauGia = specDauGia.and(DauGiaSpecifications.dauGiaBySelected(true))
                                .and(DauGiaSpecifications.dauGiaByShipper(currentUser.getShipper()));
                        break;
                }
            }
        }

        List<DauGia> dauGiaList = dauGiaRepository.findAll(specDauGia, pageable).getContent();

        return dauGiaMapper.toDauGiaResponseList(dauGiaList);

    }

    public List<Product> getAllProductsForAdmin() {
        // Dành cho admin để xem tất cả sản phẩm
        return productRepository.findAll();
    }

    public String createOrder(int total, String orderInfor, String urlReturn, String locate) {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = vnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = vnpayConfig.vnp_TmnCode;
        String orderType = "order-type";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);


        vnp_Params.put("vnp_Locale", locate);

        // urlReturn += vnpayConfig.secretKey;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    public void updateProduct( Product product){
        product.setPaymentDate();
        this.productRepository.save(product);
    }

    public List<Map<String, Object>> countProductsByMonth(Map<String,String> params) {
        String yearString = params.get("year");

        if(yearString.isEmpty() || yearString==null){
            Calendar calendar = Calendar.getInstance();
            yearString = calendar.get(Calendar.YEAR)+"";
        }
        int year = Integer.parseInt(yearString);
        String period = params.get("period");

        List<Object[]> resultList =productRepository.findProductsByPeriodAndCount(year,period);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> map = new HashMap<>();
            map.put(period, row[0]);
            map.put("quantity", row[1]);
            result.add(map);
        }
        return result;
    }

    public List<Map<String, Object>> countProductsByService(Map<String,String> params) {
        String yearString = params.get("year");

        if(yearString.isEmpty() || yearString==null){
            Calendar calendar = Calendar.getInstance();
            yearString = calendar.get(Calendar.YEAR)+"";
        }
        int year = Integer.parseInt(yearString);

        List<Object[]> resultList =productRepository.findProductsByServiceAndYear(year);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> map = new HashMap<>();
            map.put("serviceName", row[0]);
            map.put("quantity", row[1]);
            result.add(map);
        }
        return result;
    }

}
