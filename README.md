# Zenvy

### A Reusable Developer Tools Ecosystem

*Build faster. Reuse smarter. Ship better.*

**Zenvy** is an evolving ecosystem of reusable developer tools and libraries designed to simplify common software development tasks. It aims to reduce repetitive code, encourage consistent engineering practices, and help developers build maintainable applications more efficiently.

Instead of implementing the same utilities and infrastructure components in every project, developers can integrate the Zenvy modules they need and focus on building their application's core features.

## 🎯 Vision

To create a modular, developer-friendly toolkit that makes common software development tasks simpler, reusable, and easier to maintain across different projects.

## ✨ Key Principles

* **Modular Architecture** — Use only the modules your project needs.

* **Reusability** — Implement common functionality once and reuse it across projects.

* **Developer Experience** — Keep APIs simple, clear, and easy to integrate.

* **Maintainability** — Encourage clean code and consistent development practices.

* **Extensibility** — Design modules so that new capabilities and implementations can be added over time.

## 🧩 Modules

| Module | Purpose | Status | 
| ----- | ----- | ----- | 
| **`zenvy-core`** | Shared utilities, common exceptions, response models, and foundational types | In Development | 
| **`zenvy-storage`** | Reusable local image and file storage abstractions, implementations, and validation utils | **Active / Implemented** | 
| **`zenvy-validation`** | Reusable validation utilities and conventions | Planned | 
| **`zenvy-logging`** | Standardized logging utilities and integrations | Planned | 
| **`zenvy-security`** | Reusable security-related helpers and integrations | Planned | 
| **`zenvy-web`** | Common web components and API integration utilities | Planned | 

## 🏗️ Architecture

Zenvy follows a modular architecture in which each module has a clear responsibility:

```
Zenvy
├── zenvy-core
├── zenvy-storage
├── zenvy-validation
├── zenvy-logging
├── zenvy-security
└── zenvy-web


```

Modules are independently reusable. Applications can integrate required modules without needing to adopt the entire ecosystem.

## 📖 Comprehensive Integration Guide & Tutorial

This section provides a step-by-step tutorial on integrating **Zenvy** modules (such as `zenvy-storage`) into an external standalone Spring Boot application (e.g., `product-service`).

### Step 1: Install Zenvy Modules Locally

Because standalone applications consume Zenvy as an external Maven dependency, you must first install Zenvy into your local `.m2` repository:

```
# Clone the Zenvy ecosystem repository
git clone <your-zenvy-repository-url>
cd zenvy

# Install all modules to your local Maven repository (.m2)
mvn clean install -DskipTests


```

### Step 2: Add Dependency to Your Consuming Project

In your target project's `pom.xml` (e.g., `product-service`), add the required Zenvy module:

```
<dependency>
    <groupId>io.github.yasidup123</groupId>
    <artifactId>zenvy-storage</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>


```

### Step 3: Enable Component Scanning

Since Zenvy uses its own package structure (`io.github.yasidup123.zenvy_storage`), Spring Boot needs to know where to find its `@Service` and `@Component` beans.

Update your main application class:

```
package com.smartorder.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.smartorder.product",            // Your application package
        "io.github.yasidup123.zenvy_storage" // Zenvy Storage module package
})
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}


```

### Step 4: Configure `application.yml`

Configure the required properties in your application's `src/main/resources/application.yml`:

```
zenvy:
  storage:
    upload-dir: uploads
    max-file-size: 10485760 # 10MB in bytes
    allowed-mime-types:
      - image/jpeg
      - image/png
      - image/webp
      - application/pdf


```

### Step 5: Configure Resource Serving for Direct URL Access

To allow static file/image access over HTTP (e.g., `http://localhost:8080/uploads/products/...`), register a `WebMvcConfigurer` in your consuming application:

```
package com.smartorder.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}


```

### Step 6: Inject and Use `StorageService` in Controller

Inject `StorageService` into your REST Controller to handle incoming multipart file uploads:

```
package com.smartorder.product.controller;

import com.smartorder.product.dto.ProductRequest;
import com.smartorder.product.dto.ProductResponse;
import com.smartorder.product.service.ProductService;
import io.github.yasidup123.zenvy_storage.dto.FileUploadResponse;
import io.github.yasidup123.zenvy_storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final StorageService storageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@ModelAttribute ProductRequest request) {

        String imageUrl = null;

        // 1. Save file using zenvy-storage module if present
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            FileUploadResponse uploadResponse = storageService.store(request.getImageFile(), "products");
            imageUrl = uploadResponse.getFileUrl();
        }

        // 2. Delegate persistence logic to ProductService
        ProductResponse product = productService.createProduct(request, imageUrl);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}


```

## 🛠️ Technology Stack

* **Language:** Java 21

* **Build Tool:** Apache Maven (Multi-module setup)

* **Framework:** Spring Boot

* **Persistence & Utilities:** Spring Data JPA, MapStruct, Lombok

* **Version Control:** Git and GitHub

## 🗺️ Roadmap

* \[x\] Establish Maven multi-module structure

* \[x\] Implement reusable file & image storage module (`zenvy-storage`)

* \[ \] Implement foundational utilities in `zenvy-core`

* \[ \] Add common exception and API response models

* \[ \] Add unit & integration tests

* \[ \] Document module APIs and configurations

* \[ \] Publish stable modules to remote Maven Central / GitHub Packages

* \[ \] Expand ecosystem modules based on real-world needs

## 🤝 Contributing

Contributions, suggestions, bug reports, and feature requests are welcome!

1. Does it solve a common development problem?

2. Can it be reused across multiple applications?

3. Does it have a clearly defined responsibility?

4. Can it be tested and documented independently?

## 📄 License

The project license has not yet been specified. A license will be added before public distribution.

## 👨‍💻 Maintainer

**Yasindu Pathiraja**

* GitHub: [@YasinduP123](https://github.com/YasinduP123)

*Zenvy — Reusable tools for better software development.*
