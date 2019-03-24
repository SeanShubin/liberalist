const keyValueStringToPair = (keyValueString) => {
    const parts = keyValueString.split("=");
    return {
        key: parts[0],
        value: parts[1]
    }
};

const queryToKeyValueArray = (query) => {
    const keyValueStrings = query.split("&");
    const keyValuePairs = keyValueStrings.map(keyValueStringToPair);
    return keyValuePairs;
};

const exactlyOne = (array) => {
    if (array.length === 1) {
        return array[0];
    } else {
        throw `Expected exactly 1 element in the array, got ${array.length}`
    }
};

const lookupSingleValueFromQuery = (args) => {
    const {target, queryParts, defaultValue} = args;
    const matchesTarget = (testMe) => target === testMe.key;
    const filtered = queryParts.filter(matchesTarget);
    if (filtered.length === 0) {
        return defaultValue;
    } else if (filtered.length > 1) {
        throw `Expected exactly 1 instance of query parameter ${target}, got ${filtered.length}`
    } else {
        const single = exactlyOne(filtered);
        return single.value;
    }
};

const parseUrl = (text) => {
    const scheme = '(?:([^:]*)://)?';
    const domain = '([^:/]*)?';
    const port = '(?::([^/?#]*))?';
    const path = '(?:(/[^?#]*))?';
    const query = '(?:\\?([^#]*))?';
    const fragment = '(?:#(.*))?';
    const urlRegex = new RegExp(scheme + domain + port + path + query + fragment);
    const parts = urlRegex.exec(text);
    return {
        scheme: parts[1],
        domain: parts[2],
        port: parts[3],
        path: parts[4],
        query: parts[5],
        fragment: parts[6],
        queryParts: queryToKeyValueArray(parts[5])
    };
};

const debugText = (text) => {
    const p = document.createElement('p');
    const textNode = document.createTextNode(text);
    p.appendChild(textNode);
    document.body.appendChild(p);
};

const urlToPageName = (url) => {
    const parsed = parseUrl(url);
    const target = 'page';
    const queryParts = parsed.queryParts;
    const defaultValue = 'a';
    const pageName = lookupSingleValueFromQuery({target, queryParts, defaultValue});
    return pageName
};

const model = {
    'a': {
        tabBars: [[
            {title: 'A Title', active: true},
            {title: 'C Title', active: false},
            {title: 'E Title', active: false}]],
        fragment: 'a.html'
    },
    'c': {
        tabBars: [
            [
                {title: 'A Title', active: false},
                {title: 'C Title', active: true},
                {title: 'E Title', active: false}
            ],
            [
                {title: 'C Title', active: true},
                {title: 'D Title', active: false}
            ]],
        fragment: 'b/c.html'
    },
    'd': {
        tabBars: [
            [
                {title: 'A Title', active: false},
                {title: 'C Title', active: true},
                {title: 'E Title', active: false}
            ],
            [
                {title: 'C Title', active: false},
                {title: 'D Title', active: true}
            ]],
        fragment: 'b/d.html'
    },
    'e': {
        tabBars: [
            [
                {title: 'A Title', active: false},
                {title: 'C Title', active: false},
                {title: 'E Title', active: true}
            ]],
        fragment: 'e.html'
    }
};

const createDiv = () => document.createElement('div');

const renderParagraph = (text) => {
    const p = document.createElement('p');
    p.textContent = text;
    return p;
};

const renderTabBar = (tabBar) => {
    return renderParagraph(JSON.stringify(tabBar));
};

const renderFragment = (fragmentName) => {
    return renderParagraph(JSON.stringify(fragmentName));
};

const renderPageModel = (pageModel) => {
    const {tabBars, fragment} = pageModel;
    const div = createDiv();
    tabBars.forEach((tabBar) => {
        const renderedTabBar = renderTabBar(tabBar);
        div.appendChild(renderedTabBar);
    });
    const renderedFragment = renderFragment(fragment);
    div.appendChild(renderedFragment);
    return div;
};

const render = () => {
    const url = window.location;
    const pageName = urlToPageName(url);
    const pageModel = model[pageName];
    return renderPageModel(pageModel)
};

debugText(urlToPageName(window.location));

document.body.appendChild(render());