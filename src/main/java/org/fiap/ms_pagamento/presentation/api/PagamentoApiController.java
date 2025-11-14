package org.fiap.ms_pagamento.presentation.api;

import lombok.AllArgsConstructor;
import org.fiap.ms_pagamento.core.controller.PagamentoController;
import org.fiap.ms_pagamento.core.entities.Pagamento;
import org.fiap.ms_pagamento.infrastructure.mappers.PagamentoResponseMapper;
import org.fiap.ms_pagamento.presentation.dto.pagamento.PagamentoDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pagamentos")
public class PagamentoApiController {

    private PagamentoController pagamentoController;
    private PagamentoResponseMapper mapper;

    @PostMapping("/gerar-qrCode/{idPedido}")
    public ResponseEntity<PagamentoDTO> gerarQrCodePagamentoMercadoPago(@PathVariable String idPedido){
        Pagamento pagamento= pagamentoController.geraQrCodePagamentoMercadoPago(idPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.entidadeParaDto(pagamento));
    }

    @PostMapping("/visualizar-qrCode/{idPedido}")
    public ResponseEntity<byte[]> visualizarQrCodePagamentoMercadoPago(@PathVariable String idPedido){
        byte[] qrCodePagamento= pagamentoController.vizualizarQrCodePagamentoMercadoPago(idPedido);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/png").body(qrCodePagamento);
    }

    @GetMapping("/status/{idPedido}")
    public ResponseEntity<PagamentoDTO> consultaStatusPagamento(@PathVariable String idPedido){
        Pagamento pagamento = pagamentoController.consultaStatusPagamento(idPedido);
        return ResponseEntity.ok().body(mapper.entidadeParaDto(pagamento));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> buscaPagamentos(){
        List<Pagamento> pagamentos = pagamentoController.buscaPagamentos();
        return ResponseEntity.ok().body(mapper.entidadesParaDtos(pagamentos));
    }
}