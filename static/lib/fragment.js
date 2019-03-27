const createFragmentLib = () => {
    const loadTextFromUrl = async (url) => {
        const result = await fetch(url);
        const text = await result.text();
        console.log('loaded ' + text.length + ' bytes from ' + url);
        return text;
    };

    const loadJsonFromUrl = async (url) => {
        const result = await fetch(url);
        const text = await result.json();
        console.log('loaded ' + text.length + ' bytes from ' + url);
        return text;
    };

    const textToElements = text => {
        const div = document.createElement('div');
        div.innerHTML = text;
        return div.children;
    };

    const loadElementsFromUrl = async (url) => {
        const text = await loadTextFromUrl(url);
        return textToElements(text);
    };

    return {
        loadElementsFromUrl,
        loadJsonFromUrl
    }
};
