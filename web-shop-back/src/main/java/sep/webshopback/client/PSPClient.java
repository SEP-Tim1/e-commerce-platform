package sep.webshopback.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sep.webshopback.dtos.PaymentRequestDTO;
import sep.webshopback.dtos.PaymentResponseDTO;

@FeignClient(url="${psp.url}", name = "psp")
public interface PSPClient {

    @PostMapping("request/request")
    PaymentResponseDTO create(@RequestBody PaymentRequestDTO dto);
}
