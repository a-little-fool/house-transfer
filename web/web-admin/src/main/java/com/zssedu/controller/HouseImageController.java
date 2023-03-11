package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.base.controller.BaseController;
import com.zssedu.entity.HouseImage;
import com.zssedu.result.Result;
import com.zssedu.service.HouseImageService;
import com.zssedu.util.QiniuUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author 周书胜
 * @date 2023年03月06 17:23
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {
    @Reference
    private HouseImageService houseImageService;

    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_UPLOED_SHOW = "house/upload";

    @GetMapping("/uploadShow/{houseId}/{type}")
    @PreAuthorize("hasAuthority('house.editImage')")
    public String uploadShow(@PathVariable("houseId") Long houseId,
                             @PathVariable("type") Integer type, ModelMap modelMap) {
        modelMap.addAttribute("houseId", houseId);
        modelMap.addAttribute("type", type);
        return PAGE_UPLOED_SHOW;
    }

    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    @PreAuthorize("hasAuthority('house.editImage')")
    public Result upload(@PathVariable("houseId") Long houseId,
                         @PathVariable("type") Integer type, @RequestParam("file") MultipartFile[] files) throws Exception {
        if (files != null && files.length > 0) {
            // 遍历文件保存到数据库
            for (MultipartFile file : files) {
                // 新文件名
                String newFilename = UUID.randomUUID().toString();
                // 上传到七牛云
                QiniuUtil.upload2Qiniu(file.getBytes(), newFilename);
                // 设置数据库中七牛云的图片地址

                // 新增houseImage保存到数据库
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setType(type);
                houseImage.setImageName(file.getOriginalFilename());
                houseImage.setImageUrl("http://rr1ghdi3l.hn-bkt.clouddn.com/" + newFilename);
                houseImageService.insert(houseImage);
            }
        }


        return Result.ok();
    }

    /**
     * 删除上传的图片
     * @return
     */
    @GetMapping("/delete/{houseId}/{id}")
    @PreAuthorize("hasAuthority('house.editImage')")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        houseImageService.delete(id);
        return LIST_ACTION + houseId;
    }
}
