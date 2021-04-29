#!/bin/bash
# yoinked from https://raw.githubusercontent.com/KentoNishi/BlankSort-Prototypes/master/utils/changelogGenerator.sh
git log --no-merges --format="%cd" --date=short | sort -u -r | while read DATE ; do
    echo
    echo [$DATE]
    GIT_PAGER=cat git log --no-merges --format="    * %aN: %s" --since="$DATE 00:00:00" --until="$DATE 24:00:00" | sed 's/JustinChang04/Justin Chang/g; s/r2dev2/Ronak Badhe/g'
done
