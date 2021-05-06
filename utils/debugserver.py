"""
A debug http server for testing out requests to an http server.

Before running:
python3 -m pip install flask

Run client with -s http://localhost:3000
"""

from flask import Flask, send_from_directory, request, Response, stream_with_context
import time

app = Flask(__name__)


def endpoint(end, value="alyoo there"):
    def _():
        print(end, request.get_data())
        print(request.headers)
        try:
            return value()
        except:
            return value
    _.__name__ = end
    app.route(end, methods=["GET", "POST", "PUT"])(_)

msg_stream = [
    "rO0ABXNyAAdNZXNzYWdlgM2FqJ89JXcCAARKAAl0aW1lc3RhbXBMAANtc2d0ABJMamF2YS9sYW5nL1N0cmluZztMAAhyZWNlaXZlcnEAfgABTAAGc2VuZGVycQB+AAF4cAAAAABglAZfdAALSGVsbG8gdGhlcmV0AAZKdXN0aW50AARicnVo",
    "rO0ABXNyAAdNZXNzYWdlgM2FqJ89JXcCAARKAAl0aW1lc3RhbXBMAANtc2d0ABJMamF2YS9sYW5nL1N0cmluZztMAAhyZWNlaXZlcnEAfgABTAAGc2VuZGVycQB+AAF4cAAAAABglAZfdAAOR2VuZXJhbCBLZW5vYml0AARicnVodAAGSnVzdGlu",
    "rO0ABXNyAAdNZXNzYWdlgM2FqJ89JXcCAARKAAl0aW1lc3RhbXBMAANtc2d0ABJMamF2YS9sYW5nL1N0cmluZztMAAhyZWNlaXZlcnEAfgABTAAGc2VuZGVycQB+AAF4cAAAAABglAZfdAASWW91IGFyZSBhIGJvbGQgb25ldAAGSnVzdGludAAEYnJ1aA=="
]

match_stream = [
    "rO0ABXNyAAVNYXRjaKNG2U9XtuJLAgADRgAFc2NvcmVMAAlmaXJzdFVzZXJ0ABJMamF2YS9sYW5nL1N0cmluZztMAApzZWNvbmRVc2VycQB+AAF4cECgAAB0AAZKdXN0aW50AARicnVo",
    "rO0ABXNyAAVNYXRjaKNG2U9XtuJLAgADRgAFc2NvcmVMAAlmaXJzdFVzZXJ0ABJMamF2YS9sYW5nL1N0cmluZztMAApzZWNvbmRVc2VycQB+AAF4cECgAAB0AAVLZXZpbnQABGJydWg=",
    "rO0ABXNyAAVNYXRjaKNG2U9XtuJLAgADRgAFc2NvcmVMAAlmaXJzdFVzZXJ0ABJMamF2YS9sYW5nL1N0cmluZztMAApzZWNvbmRVc2VycQB+AAF4cECgAAB0AAVSb25ha3QABGJydWg="
]


endpoint("/bruh")
endpoint("/message")
endpoint("/login", "rO0ABXNyAAtMb2dpblJlc3VsdHxBa652qMl8AgACWgAHc3VjY2Vzc0wABXRva2VudAASTGphdmEvbGFuZy9TdHJpbmc7eHABdAAkNTAzY2VkN2UtYTIzZS00MGZhLTg4YWQtYjIzZDJmNzU4ZTdh");
endpoint("/messages", "rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAABdAAGSnVzdGluc3IAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAB3BAAAAAB4eA==")
endpoint("/createuser", "success")
endpoint("/matches", "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IABU1hdGNoo0bZT1e24ksCAANGAAVzY29yZUwACWZpcnN0VXNlcnQAEkxqYXZhL2xhbmcvU3RyaW5nO0wACnNlY29uZFVzZXJxAH4AA3hwQKAAAHQABGJydWh0AAZqdXN0aW54")
endpoint("/listenmessages", lambda: Response(([i + "\n", time.sleep(.2)][0] for i in msg_stream)))
endpoint("/listenmatches", lambda: Response(([i + "\n", time.sleep(.2)][0] for i in match_stream)))

@app.route("/")
def index():
    print(request)
    return str(request)


if __name__ == '__main__':
    app.run(host="127.0.0.1", port=3000, debug=True)
