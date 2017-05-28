# Projeto 2 - Aplicação com suporte a mensagens seguras distribuídas

* Desenvolvimento de uma aplicação com suporte a mensagens seguras
* A aplicação deve garantir a independência de cada aplicação não devendo ser necessário um servidor central por onde as mensagens são trocadas, evitando assim que o conteúdo sensível possa ser armazenado num servidor
* Evitando a captura de tráfego as mensagens enviadas também deverão ser encriptadas com a chave pública de cada entidade para quem se pretende enviar
* Ao receber a mensagem esta pode ser desencriptada com a chaves privada (princípio PGP/GnuPG)

## Principais funcionalidades

* Troca de mensagens encriptadas entre clientes sem recurso a um servidor central
* Utilização de chaves para autenticação e encriptação de mensagens
* Definir o nome de utilizador e porto de escuta

## Plataforma alvo

* A aplicação será desenvolvida em Java com os conceitos utilizados na aula

## Serviço web

* A aplicação deverá ser capaz de comunicar com outra do mesmo tipo através da rede Tor usando TCP/IP conhecida.

## Melhorias

* A aplicação poderá suportar mensagens em grupo e transferência de ficheiros

# Como usar a aplicação

Correr os seguintes comandos:

`cd scripts/`

`sudo bash initTorHS.bash /etc/tor/torrc <destination folder for hidden service> 9550`