const urlLib = createUrlLib();
const fragmentLib = createFragmentLib();

const debugText = (text) => {
    const p = document.createElement('p');
    const textNode = document.createTextNode(text);
    p.appendChild(textNode);
    document.body.appendChild(p);
};

const urlToPageName = (url) => {
    const parsed = urlLib.parseUrl(url);
    const target = 'page';
    const queryParts = parsed.queryParts;
    const defaultValue = 'a';
    const pageName = urlLib.lookupSingleValueFromQuery({target, queryParts, defaultValue});
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

const renderFragment = async (fragmentName) => {
    return await fragmentLib.loadElementFromUrl(fragmentName)
};

const renderPageModel = async (pageModel) => {
    const {tabBars, fragment} = pageModel;
    const div = createDiv();
    tabBars.forEach((tabBar) => {
        const renderedTabBar = renderTabBar(tabBar);
        div.appendChild(renderedTabBar);
    });
    const renderedFragment = await renderFragment(fragment);
    div.appendChild(renderedFragment);
    return div;
};

const render = async () => {
    const url = window.location;
    const pageName = urlToPageName(url);
    const pageModel = model[pageName];
    return await renderPageModel(pageModel)
};

const init = async () => {
    debugText(urlToPageName(window.location));
    document.body.appendChild(await render());
};

const promise = init();
