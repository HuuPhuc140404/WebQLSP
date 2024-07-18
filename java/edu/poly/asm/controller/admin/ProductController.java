package edu.poly.asm.controller.admin;

import edu.poly.asm.domain.Category;
import edu.poly.asm.domain.Product;
import edu.poly.asm.model.CategoryDto;
import edu.poly.asm.model.ProductDto;
import edu.poly.asm.service.CategoryService;
import edu.poly.asm.service.ProductService;
import edu.poly.asm.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/products")
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    StorageService storageService;

    @ModelAttribute("categories")
    public List<CategoryDto> getCategories(){
        return categoryService.findAll().stream().map(item->{
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(item,categoryDto);
            return categoryDto;
        }).toList();
    }

    @GetMapping("add")
    public String add(Model model) {
        ProductDto productDto = new ProductDto();
        productDto.setIsEdit(false);
        model.addAttribute("product", productDto);
        return "admin/products/addOrEdit";
    }

    @GetMapping("edit/{productsId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productsId") Long productsId) {
        Optional<Product> opt = productService.findById(productsId);
        ProductDto dto = new ProductDto();
        if (opt.isPresent()) {
            Product entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("product", dto);
            return new ModelAndView("admin/products/addOrEdit", model);
        }
        model.addAttribute("message", "Product is not existed");
        return new ModelAndView("forward:/admin/products", model);
    }
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) {
        Resource file = storageService.loadFileAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("delete/{productsId}")
    public ModelAndView delete(ModelMap model, @PathVariable("productsId") Long productId)
       throws IOException {
            Optional<Product> opt = productService.findById(productId);
            if (opt.isPresent()) {
                if (!StringUtils.isEmpty(opt.get().getImage())) {
                    storageService.deleteFile(opt.get().getImage());
                }
                productService.delete(opt.get());
                model.addAttribute("message", "Product has been deleted");
            } else {
                model.addAttribute("message", "Product is not existed");
            }

        return new ModelAndView("forward:/admin/products/search", model);
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto dto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/products/addOrEdit");
        }

        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        entity.setCategory(category);

        if (!dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            entity.setImage(storageService.getStoredFilename(dto.getImageFile(), uuString));
            storageService.storeFile(dto.getImageFile(), entity.getImage());
        }

        productService.save(entity);
        model.addAttribute("message", "Product saved successfully");

        // Use redirect to avoid URL handling issues
        return new ModelAndView("forward:/admin/products", model);
    }


    @RequestMapping("")
    public String list(ModelMap model) {
      List<Product> list = (List<Product>) productService.findAll();
        model.addAttribute("products", list);
        return "admin/products/list";
    }

    @GetMapping("search")
    public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
        List<Category> list = null;
        if (StringUtils.hasText(name)) {
            list = categoryService.findByNameContaining(name);
        } else {
            list = categoryService.findAll();
        }
        model.addAttribute("products", list);
        return "admin/products/search";
    }

    @GetMapping("searchpaginated")
    public String search(ModelMap model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1; // Pageable is zero-based
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
        Page<Category> resultPage;

        if (StringUtils.hasText(name)) {
            resultPage = categoryService.findByNameContaining(name, pageable);
            model.addAttribute("name", name);
        } else {
            resultPage = categoryService.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage + 1 - 2); // Adjust for display (1-based index)
            int end = Math.min(currentPage + 1 + 2, totalPages);

            if (totalPages > 5) {
                if (end == totalPages)
                    start = end - 5;
                else if (start == 1)
                    end = start + 5;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("categoryPage", resultPage);
        return "admin/products/searchpaginated";
    }


}
