package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.dto.response.ShipperResponse;
import com.hbt.QuanLyGiaoHangBackend.exception.DuplicateFieldException;
import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import com.hbt.QuanLyGiaoHangBackend.services.CommentService;
import com.hbt.QuanLyGiaoHangBackend.services.RateService;
import com.hbt.QuanLyGiaoHangBackend.services.ShipperService;
import com.hbt.QuanLyGiaoHangBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/")
@RestController
public class ShipperController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RateService rateService;

    @Autowired
    private ShipperService shipperService;

    @Value("${PAGE_SIZE}")
    private String pageSize;

    @GetMapping("admin/getAllShipper/")
    public PagedModel<Shipper> getAllShipper(@RequestParam Map<String, String> params, PagedResourcesAssembler assembler){
        params.put("pageSize", pageSize);
        return assembler.toModel(shipperService.getAllShipper(params));
    }

    @GetMapping(path = "public/getRateOfShipper/{shipperId}")
    @CrossOrigin
    public ResponseEntity<Double> getAveragePoint(@PathVariable("shipperId") Long id){
        return new ResponseEntity<>(this.rateService.getAveragePointForShipper(id), HttpStatus.OK);
    }

    @GetMapping("customer/getShipper/{userId}")
    public ResponseEntity<ShipperResponse> getShipperByID(@PathVariable("userId") Long id){
        return new ResponseEntity<>(this.userService.getShipperInfoById(id), HttpStatus.OK);
    }

    @GetMapping("customer/getComments/{shipperId}")
    public ResponseEntity<List<?>> getCommentByShipperID(@PathVariable("shipperId") Long id){

        return new ResponseEntity<>(userService.getCommentByShipperID(id), HttpStatus.OK);
    }

    @PostMapping("customer/addComments/")
    public void postComment(@RequestParam Map<String, String> params){
        commentService.addComment(params);
    }

    @PostMapping("customer/getRate/")
    public ResponseEntity<Integer> getRate(@RequestParam Map<String, String> params){
        return new ResponseEntity<>(rateService.getOwnerPoint(params), HttpStatus.OK);
    }

    @PostMapping("customer/postRate/")
    public void postRate(@RequestParam Map<String, String> params){
        rateService.postOwnerPoint(params);
    }

    @PutMapping("admin/{id}/toggle")
    public ResponseEntity<String> toggleActive(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
        System.out.println(payload);
        boolean active = payload.get("active");
        shipperService.updateActiveStatus(id, active);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PostMapping("customer/addOrUpdateShipper/")
    public ResponseEntity<?> addOrUpdateShipper(@RequestParam Map<String, String> params
            ,  @RequestParam(required = false) MultipartFile avatar) {
        String cmnd = params.get("cmnd");

        boolean isUpdate = Boolean.parseBoolean(params.get("isUpdate"));
        try {
            shipperService.addOrUpdateShipper(cmnd, avatar,isUpdate);
            return ResponseEntity.ok("Cập nhật thành công");
        }
        catch (DuplicateFieldException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrors());
        }

    }

}
