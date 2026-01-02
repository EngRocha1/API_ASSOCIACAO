#!/bin/bash

# Este script irá criar ou modificar o arquivo /etc/wsl.conf
# com as configurações fornecidas.
#
# IMPORTANTE: Após a execução deste script, você precisará fechar
# todas as instâncias do WSL e executar 'wsl --shutdown' no PowerShell
# para que as alterações sejam aplicadas. Depois, reinicie o WSL.

echo "Criando ou modificando o arquivo /etc/wsl.conf..."

cat > /etc/wsl.conf <<EOF
[automount]
root = /mnt/

[network]
hostname = $(hostname)

[interop]
enabled = false

[experimental]
networkingMode = bridged
dnsTunneling = true
autoMountDriveFs = true
EOF

echo "Arquivo /etc/wsl.conf criado/modificado com sucesso."
echo ""
echo "Próximos passos:"
echo "1. Feche todas as janelas do seu terminal WSL."
echo "2. Execute o seguinte comando no PowerShell:"
echo "   wsl --shutdown"
echo "3. Reinicie o WSL abrindo novamente o seu terminal."
echo "4. Tente iniciar o k0s novamente: sudo k0s start"
echo "5. Verifique o status do k0scontroller: sudo systemctl status k0scontroller"
echo "6. Tente executar os comandos kubectl."
