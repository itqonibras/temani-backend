# 📌 Temani Backend Service

## ⚡️ How to Run

### 1. Install dependencies

```bash
mvn clean install
```

### 2. Run the application

```bash
mvn spring-boot:run
```

---

## 🎯 Code Formatter (Spring Java Format)

This project uses [Spring Java Format](https://github.com/spring-io/spring-javaformat) to ensure consistent code style.

### Format manually via CLI

```bash
mvn spring-javaformat:apply
```

### Validate format (without applying changes)

```bash
mvn spring-javaformat:validate
```

### VS Code setup

1. Install the extension:
   **Spring Java Format** (`io.spring.javaformat.spring-javaformat-vscode-extension`)

2. Add to your VS Code `settings.json`:

```json
{
  "[java]": {
    "editor.defaultFormatter": "io.spring.javaformat.spring-javaformat-vscode-extension"
  },
  "editor.formatOnSave": true
}
```
