package com.xsyro.jamiu.ingest;

import com.xsyro.jamiu.payload.Egress;
import com.xsyro.jamiu.payload.Ingress;
import com.xsyro.jamiu.services.IngressService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api/api-billing")
@Validated
public class HttpController {
    private final IngressService ingressService;

    //
    public HttpController(IngressService ingressService) {
        this.ingressService = ingressService;
    }

    @PostMapping("/ingest")
    private Mono<ResponseEntity<Egress>> ingest(@Valid @RequestBody Ingress ingress) {
//        ingressService.ingestData(ingress)
//                .onErrorReturn(throwable ->)
        return Mono.empty();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Egress> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(Egress.builder()
                .isSuccessful(false)
                .msg("not valid due to validation error: " + e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }


}
