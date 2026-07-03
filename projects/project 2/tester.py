import subprocess
import time
import fcntl
import os

in_file = "/home/danilo/src/covidados/testes/input2.txt"
out_file = "/home/danilo/src/covidados/testes/output2.txt"
os.chdir("/home/danilo/src/covidados/out/production/covidados")
sleep_time = 0.01
command = ["java", "Main"]

def readlines(file_path):
    with open(file_path) as file:
        return file.readlines()

in_lines = readlines(in_file)
expected_out_lines = readlines(out_file)


def compare_outputs(in_lines, expected_out_lines, p):
    out_fd = p.stdout.fileno()
    flag = fcntl.fcntl(out_fd, fcntl.F_GETFL)
    fcntl.fcntl(p.stdout.fileno(), fcntl.F_SETFL, flag | os.O_NONBLOCK)
    time.sleep(0.5)
    hold = 0
    for in_line in in_lines:
        bytes_in_line = bytes(in_line, encoding="ASCII")
        p.stdin.write(bytes_in_line)
        p.stdin.flush()
        time.sleep(sleep_time)
        out_lines = []
        print("in line:      '%s'" % bytes_in_line)
        while True:
            add_line = p.stdout.readline()
            if add_line is b"" or add_line == b"> ":
                if add_line == b"> ":
                    hold = hold + 1
                break
            else:
                if hold > 0:
                    out_lines.append(b"> " + add_line)
                else:
                    out_lines.append(add_line)
        for outline in out_lines:
            actual_out = outline
            expected_out = expected_out_lines.pop(0)
            bytes_expected_out = bytes(expected_out, encoding="ASCII")
            if bytes_expected_out == actual_out or bytes_expected_out == actual_out[2:]:
                print("out line:     '%s'" % bytes_expected_out)
            else:
                print("expected out: '%s'" % bytes_expected_out)
                print("actual out:   '%s'" % actual_out)


with subprocess.Popen(command, stdin=subprocess.PIPE, stdout=subprocess.PIPE) as p:
    compare_outputs(in_lines, expected_out_lines, p)
