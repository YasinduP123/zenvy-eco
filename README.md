# Zenvy

### A Reusable Developer Tools Ecosystem

**Build faster. Reuse smarter. Ship better.**

Zenvy is an evolving ecosystem of reusable developer tools and libraries designed to simplify common software development tasks. It aims to reduce repetitive code, encourage consistent engineering practices, and help developers build maintainable applications more efficiently.

Instead of implementing the same utilities and infrastructure components in every project, developers can integrate the Zenvy modules they need and focus on building their application's core features.

## 🎯 Vision

To create a modular, developer-friendly toolkit that makes common software development tasks simpler, reusable, and easier to maintain across different projects.

## ✨ Key Principles

- **Modular Architecture** — Use only the modules your project needs.
- **Reusability** — Implement common functionality once and reuse it across projects.
- **Developer Experience** — Keep APIs simple, clear, and easy to integrate.
- **Maintainability** — Encourage clean code and consistent development practices.
- **Extensibility** — Design modules so that new capabilities and implementations can be added over time.

## 🧩 Modules

| Module | Purpose | Status |
|---|---|---|
| `zenvy-core` | Shared utilities, common exceptions, response models, and foundational types | In development |
| `zenvy-storage` | Reusable image and file storage abstractions and implementations | In development |
| `zenvy-validation` | Reusable validation utilities and conventions | Planned |
| `zenvy-logging` | Standardized logging utilities and integrations | Planned |
| `zenvy-security` | Reusable security-related helpers and integrations | Planned |
| `zenvy-web` | Common web components and API integration utilities | Planned |

> Module availability and supported features will be documented as each module is implemented and released.

## 🏗️ Architecture

Zenvy follows a modular architecture in which each module has a clear responsibility.

```text
Zenvy
├── zenvy-core
├── zenvy-storage
├── zenvy-validation
├── zenvy-logging
├── zenvy-security
└── zenvy-web
```

Modules are intended to be independently reusable. Applications can integrate the required modules without needing to adopt the entire ecosystem.

## 🛠️ Technology Stack

The initial development focuses on the Java ecosystem.

- **Language:** Java
- **Build Tool:** Apache Maven
- **Framework Integration:** Spring Framework / Spring Boot where appropriate
- **Testing:** JUnit and Spring testing tools where applicable
- **Version Control:** Git and GitHub

Specific Java and framework versions, compatibility requirements, and supported environments will be documented for individual module releases.

## 🚀 Getting Started

### Prerequisites

- JDK 21 or a version supported by the selected module
- Apache Maven
- Git

### Clone the Repository

```bash
git clone <your-zenvy-repository-url>
cd zenvy
```

### Build the Project

```bash
mvn clean install
```

The command above applies when the repository is configured as a Maven multi-module project with a root `pom.xml`.

### Use a Module

Once a module is packaged and published to a Maven repository, add its dependency to your application's `pom.xml`.

For example, the coordinates could follow this pattern:

```xml
<dependency>
    <groupId>io.github.yasidup123</groupId>
    <artifactId>zenvy-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

Replace the example version with an actual released version. Until a module is published, it can be installed locally with Maven or consumed through an appropriate multi-module project setup.

## 🗺️ Roadmap

- [ ] Establish the Maven project structure
- [ ] Implement foundational utilities in `zenvy-core`
- [ ] Add common exception and API response models
- [ ] Implement reusable image and file storage functionality
- [ ] Add unit tests and integration tests
- [ ] Document module APIs and configuration
- [ ] Introduce versioning and release management
- [ ] Publish stable modules to a Maven repository
- [ ] Expand the ecosystem based on real-world developer needs

## 🤝 Contributing

Contributions, suggestions, bug reports, and feature requests are welcome.

When proposing a new module or feature, consider:

1. Does it solve a common development problem?
2. Can it be reused across multiple applications?
3. Does it have a clearly defined responsibility?
4. Can it be tested and documented independently?

Contribution guidelines will be added as the project matures.

## 📄 License

The project license has not yet been specified. A license will be added before public distribution so that usage, modification, and redistribution terms are clear.

## 👨‍💻 Maintainer

**Yasindu Pathiraja**

- GitHub: [@YasiduP123](https://github.com/YasiduP123)

---

*Zenvy — Reusable tools for better software development.*
