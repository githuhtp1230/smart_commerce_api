# Hướng dẫn sử dụng Permission System

## Tổng quan

Hệ thống permission được thiết kế để kiểm soát quyền truy cập vào các API endpoint dựa trên role và permission của user.

## Cấu trúc Database

### Bảng `permissions`

- `id`: Primary key
- `name`: Tên permission (ví dụ: USER_READ, PRODUCT_CREATE)
- `value`: Mô tả permission

### Bảng `roles`

- `id`: Primary key
- `name`: Tên role (ví dụ: ADMIN, MANAGER, USER)

### Bảng `role_permissions`

- `id`: Primary key
- `role_id`: Foreign key đến bảng roles
- `permission_id`: Foreign key đến bảng permissions

### Bảng `users`

- `role`: Foreign key đến bảng roles

## Cách sử dụng

### 1. Thêm annotation @HasPermission vào method

```java
@GetMapping
@HasPermission(Permissions.USER_READ)
public ApiResponse<List<UserResponse>> getAllUsers() {
    // Method implementation
}

@PostMapping
@HasPermission(Permissions.USER_CREATE)
public ApiResponse<UserResponse> createUser(@RequestBody UserRequest request) {
    // Method implementation
}
```

### 2. Sử dụng constants từ class Permissions

```java
import com.shop.smart_commerce_api.constant.Permissions;

@HasPermission(Permissions.PRODUCT_UPDATE)
public ApiResponse<ProductResponse> updateProduct() {
    // Method implementation
}
```

### 3. Kiểm tra permission trong code

```java
@Autowired
private CustomPermissionEvaluator permissionEvaluator;

public void someMethod() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (permissionEvaluator.hasPermission(authentication, Permissions.USER_DELETE)) {
        // User có quyền xóa user
    } else {
        throw new AppException(ErrorCode.ACCESS_DENIED);
    }
}
```

## Các Permission có sẵn

### User Permissions

- `USER_READ`: Đọc thông tin user
- `USER_CREATE`: Tạo user mới
- `USER_UPDATE`: Cập nhật thông tin user
- `USER_DELETE`: Xóa user

### Product Permissions

- `PRODUCT_READ`: Đọc thông tin sản phẩm
- `PRODUCT_CREATE`: Tạo sản phẩm mới
- `PRODUCT_UPDATE`: Cập nhật sản phẩm
- `PRODUCT_DELETE`: Xóa sản phẩm

### Category Permissions

- `CATEGORY_READ`: Đọc thông tin danh mục
- `CATEGORY_CREATE`: Tạo danh mục mới
- `CATEGORY_UPDATE`: Cập nhật danh mục
- `CATEGORY_DELETE`: Xóa danh mục

### Order Permissions

- `ORDER_READ`: Đọc thông tin đơn hàng
- `ORDER_CREATE`: Tạo đơn hàng mới
- `ORDER_UPDATE`: Cập nhật đơn hàng
- `ORDER_DELETE`: Xóa đơn hàng

### Payment Permissions

- `PAYMENT_READ`: Đọc thông tin thanh toán
- `PAYMENT_CREATE`: Tạo thanh toán mới
- `PAYMENT_UPDATE`: Cập nhật thanh toán

### Promotion Permissions

- `PROMOTION_READ`: Đọc thông tin khuyến mãi
- `PROMOTION_CREATE`: Tạo khuyến mãi mới
- `PROMOTION_UPDATE`: Cập nhật khuyến mãi
- `PROMOTION_DELETE`: Xóa khuyến mãi

### Review Permissions

- `REVIEW_READ`: Đọc thông tin đánh giá
- `REVIEW_CREATE`: Tạo đánh giá mới
- `REVIEW_UPDATE`: Cập nhật đánh giá
- `REVIEW_DELETE`: Xóa đánh giá

### Attribute Permissions

- `ATTRIBUTE_READ`: Đọc thông tin thuộc tính
- `ATTRIBUTE_CREATE`: Tạo thuộc tính mới
- `ATTRIBUTE_UPDATE`: Cập nhật thuộc tính
- `ATTRIBUTE_DELETE`: Xóa thuộc tính

## Các Role mặc định

### ADMIN

- Có tất cả permissions
- Có thể truy cập mọi chức năng

### MANAGER

- Có quyền đọc, tạo, cập nhật (không có quyền xóa)
- Không thể xóa user, product, category, order, promotion

### USER

- Chỉ có quyền đọc và tạo order
- Có thể tạo và cập nhật review của mình
- Không thể truy cập các chức năng quản trị

## Setup Database

1. Chạy script SQL trong file `data/permissions.sql` để tạo permissions và role-permissions mẫu
2. Đảm bảo user có role được gán đúng trong database

## Lưu ý

- Annotation `@HasPermission` sẽ tự động kiểm tra permission trước khi thực thi method
- Nếu user không có permission, sẽ throw `AppException` với `ErrorCode.ACCESS_DENIED`
- Có thể sử dụng nhiều annotation `@HasPermission` trên cùng một method
- Permission check được thực hiện thông qua AOP (Aspect-Oriented Programming)
