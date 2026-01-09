# language: pt
Funcionalidade: Controle de Pagamentos
  Como um cliente
  Eu quero gerar o QR Code para pagamento e consultar o status
  Para que eu possa finalizar meu pedido

  Cenário: Gerar QR Code para um novo pedido
    Dado que tenho um pedido pendente com ID "pedido-123"
    Quando eu solicito a geração do QR Code de pagamento para este pedido
    Então o sistema deve retornar os dados do pagamento com status "PENDENTE_PAGAMENTO"
    E deve conter o código do QR Code (QR Data)

  Cenário: Consultar status de pagamento de um pedido
    Dado que existe um pagamento processado para o pedido "pedido-456" com status "PAGO"
    Quando eu consulto o status do pagamento pelo ID do pedido "pedido-456"
    Então o sistema deve retornar que o status é "PAGO"