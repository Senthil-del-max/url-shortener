console.log("script loaded");

async function shortenUrl() {

    const url = document.getElementById("url").value;
    const alias = document.getElementById("alias").value;

    try {

        const response = await fetch("/shorten", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                url: url,
                customAlias: alias
            })
        });

        if (!response.ok) {
            throw new Error(
                "Server Error : " + response.status
            );
        }

        const data = await response.json();

        console.log(data);

        // Fix localhost URLs automatically
        let shortUrl = data.shortUrl.replace(
            "http://localhost:8080",
            window.location.origin
        );

        document.getElementById("result").innerHTML = `

            <h2>Short URL</h2>

            <input
                type="text"
                id="shortUrl"
                value="${shortUrl}"
                readonly>

            <br><br>

            <button onclick="copyUrl()">
                Copy URL
            </button>

            <br><br>

            <a href="${shortUrl}" target="_blank">
                Open Short URL
            </a>

            <br><br>

            <h2>QR Code</h2>

            <img
                src="${data.qrCode}"
                width="250"
                height="250"
                alt="QR Code">

        `;

    }
    catch (error) {

        console.error(error);

        document.getElementById("result").innerHTML =
            `<p style="color:red;">
                ${error}
             </p>`;
    }
}

function copyUrl() {

    const copyText =
        document.getElementById("shortUrl");

    navigator.clipboard.writeText(
        copyText.value
    );

    alert("URL Copied Successfully!");
}