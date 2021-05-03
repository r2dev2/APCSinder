#!/usr/bin/env python3
import re
import sys
import os

def do_stuff(fn):
    with open(fn, 'r') as fin:
        file = fin.read()

    method_re = re.compile(
        r"(public [^\n]+)"
        # r"(public [^\W]+ [^\W]+[^\n]+)"
    )

    print("Properties and methods of", fn)
    print(*re.findall(method_re, file), sep='\n')
    print()

list(map(do_stuff, sys.argv[1:]))
