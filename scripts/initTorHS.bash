#!/bin/bash

TORCONF=$1;
HIDDENSERVICEFOLDER=$2;
TORLISTENPORT=$3;


echo "Este script vai verificar se o ficheiro de configuracao do tor ten o Tor activo"
echo "Se nao tiver o tor activo vai tentar criar uma pasta segundo o segundo parametro e introduzir as configuracoes correctas e o terceiro a porta"
echo "Exemplo: bash initTorHS /etc/tor/torrc /home/utilizador/hs/ 9550"


# criar uma pasta para onde vao as chaves
rm -rf $HIDDENSERVICEFOLDER
mkdir -p $HIDDENSERVICEFOLDER
# ir a pasta onde estao as chaves e alterar as permissoes
sudo su -c "chmod 777 $HIDDENSERVICEFOLDER"


if ! grep "^HiddenServiceDir" $TORCONF 
then
	if ! grep "^HiddenServicePort" $TORCONF
	then
		sudo su -c "echo \"HiddenServiceDir $HIDDENSERVICEFOLDER\">> $1"
		sudo su -c "echo \"HiddenServicePort 80 127.0.0.1:8080\">> $1"
	fi
fi

if ! grep "^SocksListenAddress" $TORCONF 
then
	if ! grep "^SocksPort" $TORCONF
	then
		sudo su -c "echo \"SocksListenAddress 127.0.0.1:$TORLISTENPORT\">> $1"
		sudo su -c "echo \"HiddenServicePort $TORLISTENPORT\">> $1"
	fi
fi


echo Done
tor -f $TORCONF &

sleep 10

HOSTNAME_ADDRESS=$(cat $HIDDENSERVICEFOLDER/hostname)
echo "Tor Address: $hostname_address"

gpg --gen-key

gpg --export -a "sdis2017" > ../bin/keys/Public/$HOSTNAME_ADDRESS.onion-private.key
gpg --export-secret-key  -a "sdis2017" > ../bin/keys/Private/$HOSTNAME_ADDRESS.onion-private.key


cd ../

ant
ant CryptoChat

exit 0
