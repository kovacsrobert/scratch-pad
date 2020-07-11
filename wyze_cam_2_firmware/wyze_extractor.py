#!/usr/bin/env python3

import sys

class FirmwarePart:
    def __init__(self, name, offset, size):
        self.name = name
        self.offset = offset
        self.size = size

firmware_parts = [
    FirmwarePart("uImage_header", 0x0, 0x40),
    FirmwarePart("uImage_kernel", 0x40, 0x200000),
    FirmwarePart("squashfs_1", 0x200040, 0x350000),
    FirmwarePart("squashfs_2", 0x550040, 0x5F0040-0x550040),
    FirmwarePart("jffs2", 0x5F0040, 11075648-0x5F0040),
]

if sys.argv[1] == "unpack":
    f = open(sys.argv[2], "rb")
    for part in firmware_parts:
        outfile = open(part.name, "wb")
        f.seek(part.offset, 0)
        data = f.read(part.size)
        outfile.write(data)
        outfile.close()
        print(f"Wrote {part.name} - {hex(len(data))} bytes")
    f.close()
