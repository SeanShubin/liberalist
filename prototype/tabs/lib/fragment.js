const createFragmentLib = () => {
    const loadTextFromUrl = async (url) => {
        const result = await fetch(url);
        const text = result.text();
        console.log('loaded ' + text.length + ' bytes from ' + url);
        return text;
    };

    const textToElement = text => {
        const domParser = new DOMParser();
        const parsed = domParser.parseFromString(text, 'text/xml');
        const element = document.createElement(parsed.documentElement.tagName);
        element.innerHTML = parsed.documentElement.innerHTML;
        return element;
    };

    const loadElementFromUrl = async (url) => {
        const text = await loadTextFromUrl(url);
        return textToElement(text);
    };

    return {
        loadElementFromUrl
    }
};
