package com.example.E_Kart.controller;

import com.example.E_Kart.model.Product;
import com.example.E_Kart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product> > getProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.ACCEPTED) ;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = service.getProductById(id);
        if( product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
      try{ Product product1= service.addOrUpdateProduct(product,imageFile);
          return  new ResponseEntity<>(product1,HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id ,@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product updateProduct = null;
        try{
            updateProduct = service.addOrUpdateProduct(product,imageFile );
            return new ResponseEntity<>(updateProduct, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
          Product product=  service.getProductById(productId);
        if( product != null)
            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        else
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product!= null){
            service.deleteProduct(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search/")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
