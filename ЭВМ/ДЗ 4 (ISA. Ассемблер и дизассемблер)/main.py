import sys

fileOpen = True
try:
    file = open(sys.argv[1], "rb").read()
except FileNotFoundError as err:
    print("File not found")
    fileOpen = False
except IndexError as err:
    print("Usage: elf-parser.py <elf-file> <output-file>")
    fileOpen = False

def to_num(nums):
    res = 0
    t = 1
    for i in nums:
        res += int(i, 16) * t
        t *= 256
    return res

def to_name(start):
    name = ''
    j = 0
    while True:
        num = list(map(hex, file[start + j:start + j +  1]))
        if num[0][2:] == '0':
            break
        name += bytes.fromhex(num[0][2:]).decode("utf8")
        j += 1
    return name

def to_hex(num):
    res = '0x'
    for i in range(0, len(num)):
        if len(num[len(num) - 1 - i][2:]) == 1:
            res += '0'
        res += num[len(num) - 1- i][2:]
    return res

def to_bin(num):
    res = ''
    for i in reversed(num):
        res += (bin(int(i, 16))[2:].zfill(8))
    return res

def to_two(bits):
    return -int(bits[0]) << len(bits) | int(bits, 2)

def get_reg(x):
    x = int(x, 2)
    if x == 0:
        return "zero"
    elif x == 1:
        return "ra"
    elif x == 2:
        return "sp"
    elif x == 3:
        return "gp"
    elif x == 4:
        return "tp"
    elif x == 5:
        return "t0"
    elif x == 6:
        return "t1"
    elif x == 7:
        return "t2"
    elif x == 8:
        return "s0"
    elif x == 9:
        return "s1"
    elif x >= 10 and x <= 17:
        return 'a' + str(x - 10)
    elif x >= 18 and x <= 27:
        return 's' + str(x - 16)
    elif x >= 28 and x <=  31:
        return 't' + str(x - 25)
    else:
        return "ERROR"

def get_command(bytes):
    if bytes[25:] == "0110111":
        const = bytes[0:20] + '0'*12
        rd = bytes[20:25]
        return ["lui", get_reg(rd), to_two(const)]
    elif bytes[25:] == "0010111":
        const = bytes[0:20] + '0'*12
        rd = bytes[20:25]
        return ["auipc", get_reg(rd), to_two(const)]
    elif bytes[25:] == "1101111":
        const = bytes[0] + bytes[1:20] + bytes[11] + bytes[1:11] + '0'
        rd = bytes[20:25]
        return ["jal", get_reg(rd), to_two(const)]
    elif bytes[25:] == "1100111":
        if bytes[17:20] == "000":
            rd = bytes[20:25]
            rs1 = bytes[12:17]
            const = bytes[0:11]
            return ["jalr", get_reg(rd), get_reg(rs1), to_two(const)]
    elif bytes[25:] == "1100011":
        const = bytes[0] + bytes[24] + bytes[1:7] + bytes[20:24] + '0'
        rs2 = bytes[7:12]
        rs1 = bytes[12:17]
        command = []

        if bytes[17:20] == "000":
            command.append("beq")
        elif bytes[17:20] == "001":
            command.append("bne")
        elif bytes[17:20] == "100":
            command.append("blt")
        elif bytes[17:20] == "101":
            command.append("bge")
        elif bytes[17:20] == "110":
            command.append("bltu")
        elif bytes[17:20] == "111":
            command.append("bgeu")
        command.append(get_reg(rs1))
        command.append(get_reg(rs2))
        command.append(to_two(const))
        return command
    elif bytes[25:] == "0000011":
        rd = get_reg(bytes[20:25])
        rs1 = get_reg(bytes[12:17])
        const = to_two(bytes[0:12])
        command = []
        if bytes[17:20] == "000":
            command.append("lb")
        elif bytes[17:20] == "001":
            command.append("lh")
        elif bytes[17:20] == "010":
            command.append("lw")
        elif bytes[17:20] == "100":
            command.append("lbu")
        elif bytes[17:20] == "101":
            command.append("lhu")
        command.append(rd)
        command.append(rs1)
        command.append(const)
        return command
    elif bytes[25:] == "0100011":
        rs1 = get_reg(bytes[12:17])
        rs2 = get_reg(bytes[7:12])
        const = to_two(bytes[0:7] + bytes[20:25])
        command = []
        if bytes[17:20] == "000":
            command.append("sb")
        elif bytes[17:20] == "001":
            command.append("sh")
        elif bytes[17:20] == "010":
            command.append("sw")
        command.append(rs1)
        command.append(rs2)
        command.append(const)
        return command
    elif bytes[25:] == "0010011":
        rd = get_reg(bytes[20:25])
        rs1 = get_reg(bytes[12:17])
        const = to_two(bytes[0:12])
        shamt = to_two(bytes[7:12])
        command = []

        if bytes[17:20] == "001":
            return ["slli", rd, rs1, shamt]
        elif bytes[17:20] == "101":
            if bytes[0:7] == "0000000":
                return ["srli", rd, rs1, shamt]
            elif bytes[0:7] == "0100000":
                return ["srai", rd, rs1, shamt]

        if bytes[17:20] == "000":
            command.append("addi")
        elif bytes[17:20] == "010":
            command.append("slti")
        elif bytes[17:20] == "011":
            command.append("sltiu")
        elif bytes[17:20] == "100":
            command.append("xori")
        elif bytes[17:20] == "110":
            command.append("ori")
        elif bytes[17:20] == "111":
            command.append("andi")
        command.append(rd)
        command.append(rs1)
        command.append(const)
        return command
    elif bytes[25:] == "0110011":
        rd = get_reg(bytes[20:25])
        rs1 = get_reg(bytes[12:17])
        rs2 = get_reg(bytes[7:12])
        command = []

        if bytes[17:20] == "000":
            if bytes[0:7] == "0000000":
                command.append("add")
            elif bytes[0:7] == "0100000":
                command.append("sub")
        elif bytes[17:20] == "001" and bytes[0:7] == "0000000":
            command.append("sll")
        elif bytes[17:20] == "010" and bytes[0:7] == "0000000":
            command.append("slt")
        elif bytes[17:20] == "011" and bytes[0:7] == "0000000":
            command.append("sltu")
        elif bytes[17:20] == "100" and bytes[0:7] == "0000000":
            command.append("xor")
        elif bytes[17:20] == "101" and bytes[0:7] == "0000000":
            command.append("srl")
        elif bytes[17:20] == "101" and bytes[0:7] == "0100000":
            command.append("sra")
        elif bytes[17:20] == "110" and bytes[0:7] == "0000000":
            command.append("or")
        elif bytes[17:20] == "111" and bytes[0:7] == "0000000":
            command.append("and")
        command.append(rd)
        command.append(rs1)
        command.append(rs2)
        return command
    elif bytes[25:] == "1110011" and bytes[0:25] == "0000000000000000000000000":
        return ["ecall"]
    elif bytes[25:] == "1110011" and bytes[0:25] == "0000000000010000000000000":
        return ["ebreak"]
    else:
        return ["unknown_command"]

def get_bind(x):
    if x == 0:
        return "LOCAL"
    elif x == 1:
        return "GLOBAL"
    elif x == 2:
        return "WEAK"
    elif x == 10:
        return "LOOS"
    elif x == 12:
        return "HIOS"
    elif x == 13:
        return "LOPROC"
    elif x == 15:
        return "HIPROC"

def get_type(x):
    if x == 0:
        return "NOTYPE"
    elif x == 1:
        return "OBJECT"
    elif x == 2:
        return "FUNC"
    elif x == 3:
        return "SECTION"
    elif x == 4:
        return "FILE"
    elif x == 5:
        return "COMMON"
    elif x == 6:
        return "TLS"
    elif x == 10:
        return "LOOS"
    elif x == 12:
        return "HIOS"
    elif x == 13:
        return "LOPROC"
    elif x == 15:
        return "HIPROC"

def get_vis(x):
    if x == 0:
        return "DEFAULT"
    elif x == 1:
        return "INTERNAL"
    elif x == 2:
        return "HIDDEN"
    elif x == 3:
        return "PROTECTED"
    elif x == 4:
        return "EXPORTED"
    elif x == 5:
        return "SINGLETON"
    elif x == 6:
        return "ELIMINATE"

def get_index(x):
    if x == 0:
        return "UNDEF"
    elif x == 65280: # ff00
        return "LORESERVE"
    elif x == 65281: # ff01
        return "AFTER"
    elif x == 65311: # ff1f
        return "HIPROC"
    elif x == 65312: # ff20
        return "LOOS"
    elif x == 65343: # ff3f
        return "HIOS"
    elif x == 65521: # fff1
        return "ABS"
    elif x == 65522: # fff2
        return "COMMON"
    elif x == 65535: # ffff
        return "XINDEX"
    else:
        return x

def parsing_header(e_shoff, e_shnum, e_shstrndx):
    header_tables = to_num(e_shoff) + (to_num(e_shstrndx)) * 40
    name_table = to_num(list(map(hex, file[header_tables + 16:header_tables + 20])))
    sh_name = []

    for i in range(to_num(e_shnum)):
        sh_name.append(to_num(list(map(hex, file[to_num(e_shoff) + 40 * i:to_num(e_shoff) + 40 * i + 4]))))

    symtab_index = -1
    text_index = -1

    index = 0
    names = []
    for i in sh_name:
        name = ''
        while True:
            num = list(map(hex, file[name_table + i:name_table + i + 1]))
            if num[0][2:] == '0':
                break
            name += bytes.fromhex(num[0][2:]).decode("utf8")
            i += 1
        names.append(name)
        if name == '.symtab':
            symtab_index = index
        if name == '.text':
            text_index = index
        index += 1

    return ( 
        to_num(list(map(hex, file[to_num(e_shoff) + 40 * symtab_index + 16:
                  to_num(e_shoff) + 40 * symtab_index + 20]))), symtab_index,
        to_num(list(map(hex, file[to_num(e_shoff) + 40 * text_index + 16:
                  to_num(e_shoff) + 40 * text_index + 20]))), text_index
    )

def parsing_text(text_offset, text_index, e_shoff, out):
    size = to_num(list(map(hex, file[to_num(e_shoff) + 40 * text_index + 20:to_num(e_shoff) + 40 * text_index + 24])))
    adress =  to_hex(list(map(hex, file[to_num(e_shoff) + 40 * text_index + 12:to_num(e_shoff) + 40 * text_index + 16])))

    adress = int(adress, 16)
    for i in range(size // 4):
        bytes = to_bin(list(map(hex, file[text_offset + i * 4:text_offset + i * 4 + 4])))
        command = get_command(bytes)

        out.write("%08x "%(adress))
        if i == 0:
            out.write("%10s: "%("_start"))
        else:
            out.write("%10s  "%(" "))
        for j in range(len(command)):
            if j != 0 and j != len(command) - 1:
                out.write('%s, '%(str(command[j])))
            else:
                out.write('%s '%(str(command[j])))
        out.write('\n')

        adress += 4

def parsing_symtab(symtab_offest, symtab_index, e_shoff, e_shstrndx, out):
    idk = to_num(e_shoff) + (to_num(e_shstrndx) - 1) * 40
    start = to_num(list(map(hex, file[idk + 16:idk + 20])))

    sizeSymtab = to_num(list(map(hex, file[to_num(e_shoff) + 40 * symtab_index + 20:to_num(e_shoff) + 40 * symtab_index + 24])))
    rowsCnt = sizeSymtab // 16

    out.write("%s %-15s %7s %-8s %-8s %-8s %6s %s\n"%("Symbol", "Value", "Size", "Type", "Bind", "Vis", "Index", "Name"))
    for i in range(rowsCnt):
        row = []


        value = list(map(hex, file[symtab_offest + i * 16 + 4:symtab_offest + i * 16 + 8]))
        row.append(int(to_hex(value), 16))

        size = list(map(hex, file[symtab_offest + i * 16 + 8:symtab_offest + i * 16 + 12]))
        row.append(int(to_hex(size), 16))

        # Add Type and Bind
        tb = list(map(hex, file[symtab_offest + i * 16 + 12:symtab_offest + i * 16 + 13]))
        tb = bin(int(tb[0], 16))[2:].zfill(8)
        # Type
        row.append(get_type(int(tb[4:8], 2)))
        # Bind
        row.append(get_bind(int(tb[0:4], 2)))
    
        vis = list(map(hex, file[symtab_offest + i * 16 + 13:symtab_offest + i * 16 + 14]))
        vis = bin(int(vis[0], 16))[2:].zfill(8)
        row.append(get_vis(int(vis, 2)))

        index = list(map(hex, file[symtab_offest + i * 16 + 14:symtab_offest + i * 16 + 16]))
        index = to_num(index)
        row.append(get_index(index))

        name = to_num(list(map(hex, file[symtab_offest + i * 16:symtab_offest + i * 16 + 4])))
        if name == 0:
            row.append("")
        else:
            row.append(to_name(start + name))

        out.write("[%4i] 0x%-15X %5i %-8s %-8s %-8s %6s %s\n"%(i, row[0], int(row[1]), row[2], row[3], row[4], row[5], row[6]))

if __name__ == "__main__" and fileOpen:
    try:
        magic_nums = list(map(hex, file[0:4]))
        if magic_nums[0] != '0x7f' or magic_nums[1] != '0x45' or magic_nums[2] != '0x4c' or magic_nums[3] != '0x46':
            print("Unsopported file")
        else:
            try:
                out = open(sys.argv[2], 'w')

                e_shoff = list(map(hex, file[32:36]))
                e_shnum = list(map(hex, file[48:50]))
                e_shstrndx = list(map(hex, file[50:52]))

                symtab_offset, symtab_index, text_offset, text_index = parsing_header(e_shoff, e_shnum, e_shstrndx)
                parsing_text(text_offset, text_index, e_shoff, out)
                out.write('\n')
                parsing_symtab(symtab_offset, symtab_index, e_shoff, e_shstrndx, out)

                out.close()
            except IndexError as err:
                print("Usage: elf-parser.py <elf-file> <output-file>")
    except IndexError as err:
        print("File is empty")