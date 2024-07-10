function showSuggestions(query) {
    if (query.length === 0) {
        document.getElementById("autoFrame").style.display = "none";
        document.getElementById("autoFrame").innerHTML = "";
        return;
    }
    console.log(query);
    fetch("http://localhost:9002/autocomplete/" + encodeURIComponent(query), {
        method: "GET",
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        }
    })
    .then(response => response.json())
    .then(data => {
        let suggestions = data.map(item => `<div class="suggestion" onclick="selectSuggestion('${item.keyword}')">${item.keyword}</div>`).join("");
        document.getElementById("autoFrame").style.display = "block";
        document.getElementById("autoFrame").innerHTML = suggestions;
    });
}

function selectSuggestion(suggestion) {
    document.getElementById("search-input").value = suggestion;
    document.getElementById("autoFrame").style.display = "none";
    document.getElementById("autoFrame").innerHTML = "";
}

function f(query) {
    return fetch("http://localhost:9002/search/" + encodeURIComponent(query), {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log('Search results:', data);
    });
}

function handleSubmit(event) {
    event.preventDefault(); // 폼 제출 방지
    const query = document.getElementById('search-input').value;
    f(query)
    .then(() => {
        document.getElementById('form').submit(); // 검색어 저장 후 폼 제출
    })
    .catch(error => {
        console.error('Error sending search query:', error);
    });
    return false;
}
