#!/bin/bash

TORCONF=$1;
HIDDENSERVICEFOLDER=$2


echo "Este script vai verificar se o ficheiro de configuracao do tor ten o Tor activo"
echo "Se nao tiver o tor activo vai tentar criar uma pasta segundo o segundo parametro e introduzir as configuracoes correctas"
echo "Exemplo: bash initTorHS /etc/tor/torrc /home/utilizador/hs/"


if ! grep "^HiddenServiceDir" $TORCONF 
then
	if ! grep "^HiddenServicePort" $TORCONF
	then
		sudo su -c "echo \"HiddenServiceDir $HIDDENSERVICEFOLDER\">> $1"
		sudo su -c "echo \"HiddenServicePort 80 127.0.0.1:8080\">> $1"
	fi
fi

mkdir -p $HIDDENSERVICEFOLDER
chmod 700 $HIDDENSERVICEFOLDER
echo Done
tor -f $TORCONF
exit 0
