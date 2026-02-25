## Request Handling

```mermaid
---
title: Generic Response Structure
---

classDiagram
    class ValidationResultLine {
    }
    
    class RespCode {
        <<enumeration>>
        Success("00")
        Error("01")
        ValidationFailed("02")
        BusinessError("03")
        NotFound("04")
    }
    class GenericResponse~Data~{
        +RespCode responseCode()
        +String responseMessage()
        +List~ValidationResultLine~ errors()
        +~Data~ data()
    }
    
    class Object{
    }
        
    class Data{
    }
    
    Object <|-- Data
    
    GenericResponse *-- RespCode
    GenericResponse o-- ValidationResultLine
    note for Data "Response data object \nsubstituting generic type `Data` "
    GenericResponse o-- Data
```

### Request execution
#### Success
![image](https://img.plantuml.biz/plantuml/png/FOux3W8n30NxdC9oJR7yfQrMWeQ24o2AA3OB5F7AzbPHxaxuY7BUJF46ywZ-DCcX1010PPiEGemzTXqk_fjaupERnV6joZ5wX1jxkZ_o23Qdr5pzzQmgQjZ3vVeTgDKD2qugD2S_KIDShjQRxMwFDTGmSARFYnS0)

```java


@PostMapping("doSomething")
public GenericResponse<String> doSomething() {
    return GenericResponse.data("success");
}
```


#### Not found
![image](https://img.plantuml.biz/plantuml/png/FOsx2e0m34NtVaN8xE3Wv2GukxYAGx5Ha998qaxYl_l4yPnpuJQMlARLXDtk002LR6ib4DPOLbZyJjYe8pE_t6dloIJhHC2IO9RCuVZ6f2fgMCCmlYBuv2_a7ACxN4CSdhiJ)

```java


@PostMapping("findBySku")
public GenericResponse<SomeProduct> findBySku(String sku) {
    return GenericResponse.notFound();
}
```

#### Validation Failure
![image](https://img.plantuml.biz/plantuml/png/NO-n3i8m34JtVeNbkGCmTgh4pCg26Ap6HK5934uwLVbtaXHLWl7U-KxdCIRIz8mIkhK305IEhxCOnW4F9-nt9Y7oXMEaHxNMApbhA5a9mAgYu3SlVoCDHXpWLYMiE5jsfYJPatNOez-x6e5Pr5E280acKUKfOUx_icIC7d-ov1nKMW2qAr1-BxRerXGMvp3NblisgqocCghoBdSZ1zDU_m00)

```java

import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

@Autowired
Validator validator;

@PostMapping("findBySku")
public GenericResponse<SomeProduct> findBySku(@RequestBody requestData) {
    final var result = validator.validate(Frequency);
    if (!result.isEmpty()) {
        return GenericResponse.validationFail(result);
    }
    // ... real logic below ...
}
```

#### Business Error
![image](https://img.plantuml.biz/plantuml/png/SoWkIImgoIhEp-Egvb9GK50gIYqkSCvFILMoKZ8mLjA1Y-Nd5QVwfXON9wQ3fAgTIeipyu1SXTIYelmYXLo8J2rK0rYmMCbA8Qyq9mSYb99OaWWLuAhbSaZDIm5R2W00)

```java

import org.springframework.web.bind.annotation.PostMapping;

@PostMapping("doSomething")
public GenericResponse<String> doSomething() {
    try {
        // ... real logic
        if (businessError) { // failed for some business logic
            return GenericResponse.businessError("Some business error happened");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return GenericResponse.error("Some unknown error");
    }
}
```

#### Unexpected Error
![image](https://img.plantuml.biz/plantuml/png/FSqn2e0m44JHFgTOjhRGrYfWRUa1WXa4qOtiHX34kujHRDzd66yvQ9ujYJiT4R72jYv5SCjrmzLlIGmzpCBqf7CG71l6Z4XGJKhhswvlKj2u9TcNvQKOSlZ0NSv3Ohcz0G00)

```java

import org.springframework.web.bind.annotation.PostMapping;

@PostMapping("doSomething")
public GenericResponse<String> doSomething() {
    try {
        // ... real logic
    } catch (Exception e) {
        e.printStackTrace();
        return GenericResponse.error("Some unknown error");
    }
}
```

### Suggested high level request handling

```java


@PostMapping("doSomething")
public GenericResponse<String> doSomething() {
    try {
        final var result = validator.validate(Frequency);
        if (!result.isEmpty()) {
            return GenericResponse.validationFail(result);
        }
        // ... real logic
        if (businessError) {
            return GenericResponse.businessError("Some business error happened");
        } else {
            return GenericResponse.data(new Object() /* real response here*/);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return GenericResponse.error("Some unknown error");
    }
}
```
