"""
A debug http server for testing out requests to an http server.

Before running:
python3 -m pip install flask

Run client with -s http://localhost:3000
"""

from flask import Flask, send_from_directory, request
import time

app = Flask(__name__)


def endpoint(end, value="alyoo there"):
    def _():
        print(end, request.get_data())
        print(request.headers)
        return value
    _.__name__ = end
    app.route(end, methods=["GET", "POST", "PUT"])(_)


endpoint("/bruh")
endpoint("/message")
endpoint("/login", "rO0ABXNyAAtMb2dpblJlc3VsdHxBa652qMl8AgACWgAHc3VjY2Vzc0wABXRva2VudAASTGphdmEvbGFuZy9TdHJpbmc7eHABdAAkNTAzY2VkN2UtYTIzZS00MGZhLTg4YWQtYjIzZDJmNzU4ZTdh");
endpoint("/messages", "rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAABdAAGSnVzdGluc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAB3BAAAAAB4eA==")
endpoint("/createuser", "success")

@app.route("/")
def index():
    print(request)
    return str(request)


if __name__ == '__main__':
    app.run(host="127.0.0.1", port=3000, debug=True)
