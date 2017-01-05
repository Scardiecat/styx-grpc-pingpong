#!/usr/bin/env bash
curl -fSL https://github.com/subchen/frep/releases/download/v1.1.0/frep-linux-amd64.zip -o frep.zip
sudo unzip frep.zip -d /usr/local/bin/
rm frep.zip

curl -fSL https://github.com/Scardiecat/svermaker/releases/download/0.2.0/svermaker-linux-amd64.zip -o svermaker.zip
sudo unzip svermaker.zip -d /usr/local/bin/
rm svermaker.zip

curl https://raw.githubusercontent.com/kubernetes/helm/master/scripts/get | bash

helm init --client-only