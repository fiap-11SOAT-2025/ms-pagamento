package org.fiap.ms_pagamento.core.usercases.impl;

import com.google.zxing.WriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

class QrCodeServiceTest {

    private QrCodeService qrCodeService;

    @BeforeEach
    void setUp() {
        qrCodeService = new QrCodeService();
    }

    @Test
    void deveGerarQrCodeComSucesso() throws Exception {
        String codigoEmvCo = "00020101021226890014BR.GOV.BCB.PIX2575pix.mercadopago.com/qr/1234567890abcdef5204000053039865802BR5920NOME TESTE6009SAO PAULO62130509ABC123456304D8A5";

        byte[] qrCodeBytes = qrCodeService.geraQrCodePagamentoMercadoPago(codigoEmvCo);

        assertNotNull(qrCodeBytes);
        assertTrue(qrCodeBytes.length > 0, "O QR Code não deve estar vazio");

        // Verifica se o byte array realmente é uma imagem PNG válida
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(qrCodeBytes));
        assertNotNull(image, "A imagem gerada deve ser válida");
        assertEquals(300, image.getWidth());
        assertEquals(300, image.getHeight());
    }

    @Test
    void deveLancarExcecaoQuandoCodigoInvalido() {
        String codigoEmvCo = "";

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                qrCodeService.geraQrCodePagamentoMercadoPago(codigoEmvCo)
        );

        assertTrue(
                exception.getMessage().contains("Erro ao gerar QR Code")
                        || exception.getMessage().toLowerCase().contains("inválido")
                        || (exception.getCause() != null && (
                        exception.getCause() instanceof WriterException
                                || exception.getCause() instanceof IOException
                                || exception.getCause() instanceof IllegalArgumentException
                )),
                "A exceção deve estar relacionada à geração do QR Code"
        );
    }
}

