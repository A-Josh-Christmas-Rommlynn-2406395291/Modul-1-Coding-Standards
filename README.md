# Module 01

## Exercise 1: My Clean Code Principles

### My Reflection
1. **What have I implemented the principles of clean code?**: I prioritize descriptive naming to improve readability that supports clean code principles.
2. **Secure Coding Practices**: I didn't sanitize user inputs at all but I know that is a major vulnerability.
3. **How to Fix Logic Issues?**: By resolving the issue where the product form wouldn't create a product upon submission in Spring Boot.
4. **Security Improvements**: Sanitize all user inputs to prevent **SQL Injection** (such as using **Spring Data JPA**) and **XSS Scripting** (such as **Thymeleaf**).

---

## Exercise 2: Unit Testing & Code Coverage

### How I feel after writing unit tests
After writing unit tests, I feel more confident that the program is running properly. It provides a safety net for future changes.

### Key Takeaways on Code Coverage
* **What is it?** A metric that shows how much of the source code is executed during tests.
* **The 100% Myth**: 100% code coverage does **not** mean the code is bug-free or that the logic is correct.
* **The Goal**: It only guarantees that every line was executed. To truly verify a program, I must implement diverse test scenarios comprehensively and robust testing practices.

# Module 02

## Reflection

### 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
- SAST security issues: By adding Scorecard workflow for supply-chain security and adding PMD code scanning workflow
- CI-Tests security issues: By adding PMD code scanning workflow
- Token-Permissions security issues: By restricting permissions for GITHUB_TOKEN by doing .github/workflows/sudo.yml, .github/workflows/ci.yml, .github/workflows/node-workflow-1.yml, 	.github/workflows/super-linter.yml, .github/workflows/workflow.yml, .github/workflows/codeql.yml

### 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
No, the implementation only partially meets the definition, with significant gaps in both CI and CD. The reasons are limited continuous integration. This implementation has only basic testing, The primary CI workflow (`ci.yml`) runs unit tests on push and pull requests, which is a fundamental CI practice. However, the implementation is lacks comprehensive CI practices such as code coverage reporting, static code analysis beyond security scanning (like PMD for code quality), or automated integration testing. While additional workflows exist (CodeQL, PMD, super-linter), they are not integrated into the core CI pipeline as required checks, meaning failures in linting or code quality analysis don't block merges, defeating the purpose of continuous integration. Besides that, this repository has zero CD implementation. There are no workflows that automatically build, package, or deploy the application to any environment (staging, production, etc.). A true CD pipeline would automatically deploy successful builds to target environments. The current setup only includes code quality and security checks but provides no mechanism for releasing or deploying the application, which is essential for continuous deployment.

# Module 03

# The SOLID Implementation
- SRP (Not fully complied, so I change the code)
- OCP (Not fully complied, so I change the code)
- LSP (Fully complied because I don't see the violation)
- ISP (Fully complied, because I don't see the violation)
- DIP (Not fully complied, so I change the code)

## Reflection
### 1. Explain what principles you apply to your project!
All principles of SOLID (Single Responsibility Principle (SRP), Open-Closed Principle (OCP), Liskov Substitution Principle (LSP), Interface Segregation Principle (ISP), Dependency Inversion Principle (DIP)) to make the code of project clean.
### 2. Explain the advantages of applying SOLID principles to your project with examples.
- Easier to test by applying single responsibility principle 
Example: 
  (@GetMapping("/editCar/{carId}")
  public String editCarPage(@PathVariable String carId, Model model) {
  Car car = carService.findById(carId);
  if (car == null) {
  return "redirect:/car/listCar";
  }
  model.addAttribute("car", car);
  return "editCar";
  })

- Reduce side effects of existing methods after adding some methods by applying open-closed principle
Example: 
  @Override
  public Product create(Product product) {
  if (product.getProductId() == null || product.getProductId().isBlank()) {
  product.setProductId(UUID.randomUUID().toString());
  }
  productRepository.create(product);
  return product;
  }

  @Override
  public List<Product> findAll() {
  Iterator<Product> productIterator = productRepository.findAll();
  List<Product> allProduct = new ArrayList<>();
  productIterator.forEachRemaining(allProduct::add);
  return allProduct;
  }

  @Override
  public Product findById(String productId) {
  return productRepository.findById(productId);
  }

  @Override
  public Product update(Product product) {
  return productRepository.update(product);
  }

  @Override
  public Product delete(Product product) {
  return productRepository.delete(product);
  }
  - Allowing add new subclasses without modifying existing, tested code when applying Liskov substitution principle.
  Example:
    class A {
    public String call() {
      System.out.println("Test call");
      return "call";
      }
    }

    class B extends A {
    @Override
    public String call() {
      System.out.println("Test print");
      return "print";
      }
    }

    public class Main {
      public static void main(String[] args) {
      A a = new B();
      String result = a.call();          
  }
}
- Reduced coupling (only specific methods they need) by applying Interface Segregation Principle
Example:
// Focused, granular interfaces
  interface Printer { void print(); }
  interface Scanner { void scan(); }
  interface Fax { void fax(); }

// Simple device only uses what it needs
class SimplePrinter implements Printer {
public void print() {
System.out.println("Printing document...");
}
}

// Multi-function device can implement multiple interfaces
class AllInOneMachine implements Printer, Scanner, Fax {
public void print() { System.out.println("Printing..."); }
public void scan() { System.out.println("Scanning..."); }
public void fax() { System.out.println("Faxing..."); }
}

- Gain loose coupling so does not ripple through the entire system by applying Dependency Inversion Principle
Example:
  // 1. The Abstraction (Both depend on this)
  interface MessageSender {
  void send(String message);
  }

// 2. Low-Level Modules (Implement the abstraction)
class EmailSender implements MessageSender {
public void send(String message) { System.out.println("Email: " + message); }
}

class SmsSender implements MessageSender {
public void send(String message) { System.out.println("SMS: " + message); }
}

// 3. High-Level Module (Depends ONLY on the abstraction)
class NotificationService {
    private final MessageSender sender;

    // Dependency is "Injected" via constructor
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String msg) {
        sender.send(msg);
    }
}
### 3. Explain the disadvantages of not applying SOLID principles to your project with examples.
- Hard to change when you need to add classes because code becomes tightly coupled.
Example:
  // VIOLATION: Everything is in one class
  public class OrderProcessor {

  // VIOLATION (SRP): Handles logic, database, AND email
  public void processOrder(String orderType) {

        // VIOLATION (OCP): Hardcoded logic. Adding a new type requires modifying this file.
        if (orderType.equals("Standard")) {
            System.out.println("Processing standard order...");
        } else if (orderType.equals("Express")) {
            System.out.println("Processing express order...");
        }

        // VIOLATION (DIP): Directly instantiates a concrete low-level class
        MySQLDatabase db = new MySQLDatabase(); 
        db.saveOrder();

        // VIOLATION (ISP): Imagine an interface forced this class to also implement 'ship()' 
        // even if this specific processor doesn't handle shipping.
        
        System.out.println("Sending Email Notification...");
  }
  }

class MySQLDatabase {
public void saveOrder() { System.out.println("Saving to MySQL..."); }
}

#### Why this is a fault?
1. Violation of SRP (Single Responsibility Principle): If the email format changes, you have to edit the `OrderProcessor`. If the database schema changes, you edit the `OrderProcessor`. It has too many reasons to change.
2. Violation of OCP (Open/Closed): Every time you add a new order type (e.g., "International Delivery"), you must open this file and add another else if block, which risks breaking existing code. 
3. Violation of LSP (Liskov Substitution): If you created a `GuestOrderProcessor` that threw an error during saveOrder(), any code expecting a generic processor would crash. 
4. Violation of ISP (Interface Segregation): If this class implemented a giant `IProcess` interface with 20 methods it didn't need, it would be bloated and hard to maintain. 
5. Violation of DIP (Dependency Inversion): It is hard-linked to `MySQLDatabase`. You cannot swap it for a `MongoDatabase` or a "Mock" database for testing without rewriting the whole class.

# Module 04

## Reflection

### 1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests. 
Menurut saya, flow TDD ini cukup membantu saya dan juga membantu untuk bekerja lebih cepat (secara jangka panjang) karena ketika saya membuat test terlebih dahulu, maka saya mendapatkan guide untuk mengimplementasikan testnya meskipun saya merasa agak lambat di awal flow TDD. Tanpa FLOW TDD, memang saya cepat di awal, tapi bug-bug di akhir malah makin banyak ketika saya mencoba memperbaiki kodenya karena gk ada guide untuk memperbaiki kodenya atau mengimplementasi kodenya. Selain itu, TDD membuat saya mampu mendapatkan bug di awal tanpa debug meskipun di kasus kompleks, saya harus melakukan debug juga. Meskipun demikian, secara keseluruhan, dalam jangka panjang, TDD juga membantu saya mengurangi tenaga dan waktu untuk mengimplementasikan aplikasi yang cukup kompleks. Oleh karena itu, saya lebih nyaman menggunakan TDD dibandingkan tidak menggunakan TDD.

### 2. You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.
Menurut saya, semua test yang telah saya bikin pada tutorial ini sudah memenuhi principle F.I.R.S.T. Menurut saya, test yang saya buat sudah mengcover semua kemungkinan happy dan unhappy path,s dan saya yakin memang sudah semua kasus telah dibuat. Dan juga, terkait principle lainnya dari F.I.R.S.T., saya yakin bahwa test yang saya buat sudah memenuhi principle-principle tersebut. Hal ini dikarenakan saya juga sudah mengikuti tutorial yang terlihat sudah memenuhi principle F.I.R.S.T. Test yang saya buat tidak berjalan terlalu lama karena test tidak terlalu kompleks, saya hanya mengetest hal-hal yang dianggap penting dari setiap method. Selain itu, test tersebut juga repeatable, isolated, dan self-validating karena saya memisahkan setiap kasus test menjadi testnya sendiri dan sudah memiliki assertions yang sesuai.
