#!/usr/bin/env bash

rm -rf chartrepo

git clone --depth=1 -b gh-pages git@github.com:Scardiecat/styx-grpc-test-api.git chartrepo

# build helm chart version

export tag=$(git describe --tags)
export buildcounter=$CIRCLE_BUILD_NUM
svermaker generate $tag

source buildhelper.tmp

frep deployment/styx-grpc-pingpong/Chart.yaml.in deployment/styx-grpc-pingpong/values.yaml.in --overwrite
helm package ./deployment/styx-grpc-pingpong/

mv styx-grpc-pingpong-${svermakerBuildVersion}.tgz chartrepo/
helm repo index chartrepo/ --merge ./chartrepo/index.yaml


cd chartrepo
git add -A

git commit -m "Add styx-grpc-pingpong:${svermakerBuildVersion}"

git push

