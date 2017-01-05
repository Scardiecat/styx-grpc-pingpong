#!/usr/bin/env bash

export tag=$(git describe --tags)

svermaker generate $tag
source buildhelper.tmp

if [ "$svermakerRelease" = true ] ; then
    if GIT_DIR=./.git git rev-parse $svermakerBuildVersion >/dev/null 2>&1 ;then
       echo "Release ${svermakerBuildVersion} already exists"
    else
      git tag $svermakerBuildVersion
      git push origin $svermakerBuildVersion
    fi
fi
