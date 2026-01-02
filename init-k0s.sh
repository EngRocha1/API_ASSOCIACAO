# 3. Baixar e Instalar o k0s (Usando a versão mais recente disponível)
K0S_VERSION="v1.32.3+k0s.0"
K0S_BINARY_URL="https://github.com/k0sproject/k0s/releases/download/${K0S_VERSION}/k0s-v1.32.3+k0s.0-amd64"
wget "${K0S_BINARY_URL}" -O /tmp/k0s-linux-amd64

# Verificar se o download foi bem-sucedido
if [ -f "/tmp/k0s-linux-amd64" ]; then
    echo "Binário k0s baixado com sucesso."
    sudo install -o root -g root -m 0755 /tmp/k0s-linux-amd64 /usr/local/bin/k0s
    sudo chmod +x /usr/local/bin/k0s # Correção da permissão
else
    echo "Erro ao baixar o binário k0s. Verifique a URL e a conexão de rede."
    exit 1
fi

# 4. Inicializar o Cluster k0s (Modo Single Node para um mini sistema) - Habilitando KubeRouter
sudo k0s install controller --single --enable-feature=KubeRouter

# 5. Iniciar o Serviço k0s
sudo systemctl enable k0scontroller
sudo systemctl start k0scontroller

sudo rm /etc/apt/sources.list.d/kubernetes.list
sudo apt update
curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"

# 6. Verificar o status do k0s Controller
echo "Verificando o status do k0s Controller..."
sudo systemctl status k0scontroller

# 7. Configurar o kubectl para interagir com o cluster k0s (configura o arquivo ~/.kube/config)
mkdir -p ~/.kube
sudo k0s kubeconfig admin > ~/.kube/config
sudo chown $(id -u):$(id -g) ~/.kube/config
export KUBECONFIG=~/.kube/config

# 8. Configuração do Firewall (iptables) - Adaptado para portas k0s, KubeRouter e NodePort Ingress
sudo iptables -A INPUT -p tcp --dport 80 -j ACCEPT
sudo iptables -A INPUT -p tcp --dport 443 -j ACCEPT
sudo iptables -A INPUT -p tcp --dport 6443 -j ACCEPT # kube-apiserver
sudo iptables -A INPUT -p tcp --dport 10250 -j ACCEPT # kubelet
sudo iptables -A INPUT -p tcp --dport 10251 -j ACCEPT # kube-scheduler
sudo iptables -A INPUT -p tcp --dport 10252 -j ACCEPT # kube-controller-manager
# Portas KubeRouter (podem variar dependendo da configuração, mas essas são comuns)
sudo iptables -A INPUT -p tcp --dport 6783 -j ACCEPT # KubeRouter peer router
sudo iptables -A INPUT -p udp --dport 6783 -j ACCEPT # KubeRouter peer router
sudo iptables -A INPUT -p tcp --dport 8080 -j ACCEPT # KubeRouter health check OU sua work3-app
# Portas NodePort para o Ingress
sudo iptables -A INPUT -p tcp --dport 30080 -j ACCEPT # NodePort HTTP Ingress
sudo iptables -A INPUT -p tcp --dport 30443 -j ACCEPT # NodePort HTTPS Ingress

# 9. Salvar regras do iptables
sudo netfilter-persistent save

# 10. Verificação da Instalação
kubectl get nodes
kubectl get pods --all-namespaces

echo "Script de instalação concluído. Verifique a saída dos comandos kubectl para confirmar a instalação do k0s com KubeRouter."

