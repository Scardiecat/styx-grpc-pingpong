#!/usr/bin/env bash

rm -r chartrepo

git clone --depth=1 -b gh-pages git@github.com:Scardiecat/styx-grpc-test-api.git chartrepo

# build helm chart version

export tag=$(git describe --tags)

frep deployment/styx-grpc-pingpong/Chart.yaml.in deployment/styx-grpc-pingpong/values.yaml.in --overwrite

mv styx-grpc-pingpong-${tag}.tgz chartrepo/
helm repo index chartrepo/ --merge ./chartrepo/index.yaml


cd chartrepo
git add -A

git commit -m "Add styx-grpc-pingpong:${tag}"

git push

