package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequestDTO {

    private String apiToken;
    private long merchantOrderId;
    private LocalDateTime merchantTimestamp;
    private float amount;
    private String currency;
    private String successUrl;
    private String failureUrl;
    private String errorUrl;
    private String callbackUrl;
}
