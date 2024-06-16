I have created ecommerce project using multimodule springboot project . 
it consists of application-service as SpringBootApplication and its pom.xml as 
"<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<parent>
<groupId>com.techbellys</groupId>
<artifactId>springbootgrpc</artifactId>
<version>0.0.1-SNAPSHOT</version>
</parent>

    <artifactId>application-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>application-service</name>
    <description>application-service</description>

    <dependencies>
        <dependency>
            <groupId>com.techbellys</groupId>
            <artifactId>grpc-interface</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.techbellys</groupId>
            <artifactId>client-service</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <groupId>org.yaml</groupId>
                    <artifactId>snakeyaml</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.techbellys</groupId>
            <artifactId>server-service</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <groupId>org.yaml</groupId>
                    <artifactId>snakeyaml</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.techbellys</groupId>
            <artifactId>authentication-service</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <skip>false</skip>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>"

authentication-service with pom.xml as 

"<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<parent>
<groupId>com.techbellys</groupId>
<artifactId>springbootgrpc</artifactId>
<version>0.0.1-SNAPSHOT</version>
</parent>

    <artifactId>authentication-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>authentication-service</name>
    <description>authentication-service</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
</project>"

this module has models as 

"package com.techbellys.authenticationservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String address;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AuthUserRole> authUserRoles;

    private Date createdAt;

}
"
and "package com.techbellys.authenticationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserRole {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
"
it also has config , controller , dto , repository and service . 

another module as client-service with pom.xml as 

"
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<parent>
<groupId>com.techbellys</groupId>
<artifactId>springbootgrpc</artifactId>
<version>0.0.1-SNAPSHOT</version>
</parent>

    <artifactId>client-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>client-service</name>
    <description>client-service</description>

    <properties>
        <java.version>17</java.version>
        <protobuf.version>3.25.1</protobuf.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>net.devh</groupId>
            <artifactId>grpc-client-spring-boot-starter</artifactId>
            <version>3.0.0.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>${protobuf.version}</version>
        </dependency>

        <dependency>
            <groupId>com.techbellys</groupId>
            <artifactId>grpc-interface</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.github.javafaker</groupId>
            <artifactId>javafaker</artifactId>
            <version>1.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>2.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
        </dependency>
    </dependencies>

</project>
"
it has controller , config , dto and service . 
once controller as 

"package com.techbellys.clientservice.controller;

import com.techbellys.clientservice.dto.ProductClientRequest;
import com.techbellys.clientservice.dto.ProductClientResponse;
import com.techbellys.clientservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductClientResponse> createProduct(@RequestBody ProductClientRequest productClientRequest) {
        return new ResponseEntity<>(productService.createProduct(productClientRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductClientResponse> getProduct(@PathVariable String id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductClientResponse> updateProduct(@PathVariable String id, @RequestBody ProductClientRequest productClientRequest) {
        return new ResponseEntity<>(productService.updateProduct(id, productClientRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}"

and one service as "package com.techbellys.clientservice.service;

import com.techbellys.clientservice.component.ProductMapper;
import com.techbellys.clientservice.dto.ProductClientRequest;
import com.techbellys.clientservice.dto.ProductClientResponse;
import com.techbellys.product.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    @GrpcClient("ProductService")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    private final ProductMapper productMapper;

    public ProductClientResponse createProduct(ProductClientRequest productClientRequest) {
        CreateProductRequest serverRequest = productMapper.toServerRequest(productClientRequest);
        ProductResponse serverResponse = productServiceBlockingStub.createProduct(serverRequest);
        return productMapper.toClientResponse(serverResponse);
    }

    public ProductClientResponse getProduct(String id) {
        GetProductRequest serverRequest = GetProductRequest.newBuilder().setProductId(id).build();
        ProductResponse serverResponse = productServiceBlockingStub.getProduct(serverRequest);
        return productMapper.toClientResponse(serverResponse);
    }

    public ProductClientResponse updateProduct(String id, ProductClientRequest productClientRequest) {
        UpdateProductRequest serverRequest = productMapper.toServerUpdateRequest(id, productClientRequest);
        ProductResponse serverResponse = productServiceBlockingStub.updateProduct(serverRequest);
        return productMapper.toClientResponse(serverResponse);
    }
    public void deleteProduct(String id) {
        try {
            DeleteProductRequest serverRequest = DeleteProductRequest.newBuilder().setProductId(id).build();
            EmptyResponse response = productServiceBlockingStub.deleteProduct(serverRequest);
        } catch (Exception e) {
            log.error("Error deleting product with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
"

another module as grpc-interface with pom.xml as 

"<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<parent>
<groupId>com.techbellys</groupId>
<artifactId>springbootgrpc</artifactId>
<version>0.0.1-SNAPSHOT</version>
</parent>
<artifactId>grpc-interface</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>grpc-interface</name>
<description>grpc-interface</description>

    <properties>
        <java.version>17</java.version>
        <grpc.version>1.60.1</grpc.version>
        <protobuf.version>3.25.1</protobuf.version>

    </properties>

    <dependencies>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-netty-shaded</artifactId>
            <version>${grpc.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-protobuf</artifactId>
            <version>${grpc.version}</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-stub</artifactId>
            <version>${grpc.version}</version>
        </dependency>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>${protobuf.version}</version>
        </dependency>
        <dependency>
            <groupId>javax.annotation</groupId>
            <artifactId>javax.annotation-api</artifactId>
            <version>1.2</version>
        </dependency>
    </dependencies>

    <build>
        <extensions>
            <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>1.7.1</version>
            </extension>
        </extensions>

        <plugins>
            <plugin>
                <groupId>org.xolstice.maven.plugins</groupId>
                <artifactId>protobuf-maven-plugin</artifactId>
                <version>0.6.1</version>
                <configuration>
                    <pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}
                    </pluginArtifact>
                    <pluginId>grpc-java</pluginId>
                    <protocArtifact>com.google.protobuf:protoc:${protobuf.version}:exe:${os.detected.classifier}
                    </protocArtifact>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>compile-custom</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>"

one of its proto as 

"syntax = "proto3";

package com.techbellys.product;

option java_multiple_files = true;

import "google/protobuf/timestamp.proto";

message CreateProductRequest {
string name = 1;
float price = 2;
string categoryId = 3;
}

message GetProductRequest {
string productId = 1;
}

message UpdateProductRequest {
string productId = 1;
string name = 2;
float price = 3;
string categoryId = 4;
}

message DeleteProductRequest {
string productId = 1;
}

message ProductResponse {
string productId = 1;
string name = 2;
float price = 3;
string categoryId = 4;
}

message EmptyResponse {}

service ProductService {
rpc CreateProduct (CreateProductRequest) returns (ProductResponse);
rpc GetProduct (GetProductRequest) returns (ProductResponse);
rpc UpdateProduct (UpdateProductRequest) returns (ProductResponse);
rpc DeleteProduct (DeleteProductRequest) returns (EmptyResponse);
}"

final model as server-server with pom.xml 

"<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>
<parent>
<groupId>com.techbellys</groupId>
<artifactId>springbootgrpc</artifactId>
<version>0.0.1-SNAPSHOT</version>
</parent>
<artifactId>server-service</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>server-service</name>
<description>server-service</description>

    <properties>
        <java.version>17</java.version>
        <protobuf.version>3.25.1</protobuf.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>net.devh</groupId>
            <artifactId>grpc-server-spring-boot-starter</artifactId>
            <version>3.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>${protobuf.version}</version>
        </dependency>
        <dependency>
            <groupId>com.techbellys</groupId>
            <artifactId>grpc-interface</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>2.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
</project>"

it has config , model , repository and service 

its models as 

"package com.techbellys.serverservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

}"

"package com.techbellys.serverservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}

"

"package com.techbellys.serverservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
"

"package com.techbellys.serverservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String name;
    private float price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
"

one of service as 

"package com.techbellys.serverservice.service;

import com.techbellys.serverservice.model.Product;

public interface ProductService {
Product createProduct(String name, float price, Long categoryId);

    Product getProductById(Long id);

    Product updateProduct(Long id, String name, float price, Long categoryId);

    void deleteProduct(Long id);
}

"

"package com.techbellys.serverservice.service.impl;

import com.techbellys.serverservice.model.Category;
import com.techbellys.serverservice.model.Product;
import com.techbellys.serverservice.repository.CategoryRepository;
import com.techbellys.serverservice.repository.ProductRepository;
import com.techbellys.serverservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
@Autowired
private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(String name, float price, Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isPresent()) {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setCategory(categoryOpt.get());
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Category not found");
        }
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(Long id, String name, float price, Long categoryId) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(name);
            product.setPrice(price);

            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            if (categoryOpt.isPresent()) {
                product.setCategory(categoryOpt.get());
            } else {
                throw new IllegalArgumentException("Category not found");
            }

            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

"

and 

"package com.techbellys.serverservice.service.impl;

import com.techbellys.product.*;
import com.techbellys.serverservice.model.Product;
import com.techbellys.serverservice.service.ProductService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@Slf4j
@AllArgsConstructor
public class ProductGrpcServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductService productService;

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            Product product = productService.createProduct(request.getName(), request.getPrice(), Long.parseLong(request.getCategoryId()));
            ProductResponse response = ProductResponse.newBuilder()
                    .setProductId(product.getId().toString())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setCategoryId(product.getCategory().getId().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = productService.getProductById(Long.parseLong(request.getProductId()));
        if (product != null) {
            ProductResponse response = ProductResponse.newBuilder()
                    .setProductId(product.getId().toString())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setCategoryId(product.getCategory().getId().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new IllegalArgumentException("Product not found"));
        }
    }

    @Override
    public void updateProduct(UpdateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            Product product = productService.updateProduct(Long.parseLong(request.getProductId()), request.getName(), request.getPrice(), Long.parseLong(request.getCategoryId()));
            ProductResponse response = ProductResponse.newBuilder()
                    .setProductId(product.getId().toString())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setCategoryId(product.getCategory().getId().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<EmptyResponse> responseObserver) {
        productService.deleteProduct(Long.parseLong(request.getProductId()));
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}"

. now need to add Address model in authentication-module in seperate database .

and cart , cartitem , payment model in grpc-service in different database then authentication-module database. 





