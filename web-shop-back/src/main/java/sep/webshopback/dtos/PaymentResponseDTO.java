package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.model.PurchaseStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentResponseDTO {

    private long merchantOrderId;
    private PurchaseStatus status;
    private String message;
}
