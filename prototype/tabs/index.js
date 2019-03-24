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
            {title: 'A Title', page: 'a', active: true},
            {title: 'C Title', page: 'c', active: false},
            {title: 'E Title', page: 'e', active: false}]],
        fragment: 'a.html'
    },
    'c': {
        tabBars: [
            [
                {title: 'A Title', page: 'a', active: false},
                {title: 'C Title', page: 'c', active: true},
                {title: 'E Title', page: 'e', active: false}
            ],
            [
                {title: 'C Title', page: 'c', active: true},
                {title: 'D Title', page: 'd', active: false}
            ]],
        fragment: 'b/c.html'
    },
    'd': {
        tabBars: [
            [
                {title: 'A Title', page: 'a', active: false},
                {title: 'C Title', page: 'c', active: true},
                {title: 'E Title', page: 'e', active: false}
            ],
            [
                {title: 'C Title', page: 'c', active: false},
                {title: 'D Title', page: 'd', active: true}
            ]],
        fragment: 'b/d.html'
    },
    'e': {
        tabBars: [
            [
                {title: 'A Title', page: 'a', active: false},
                {title: 'C Title', page: 'c', active: false},
                {title: 'E Title', page: 'e', active: true}
            ]],
        fragment: 'e.html'
    }
};

const createDiv = () => document.createElement('div');
const createUl = () => document.createElement('ul');
const createLi = () => document.createElement('li');
const createLink = (args) => {
    const {href, text} = args;
    const a = document.createElement('a');
    a.href = href;
    a.text = text;
    return a;
};

const renderParagraph = (text) => {
    const p = document.createElement('p');
    p.textContent = text;
    return p;
};

const renderTabBar = (tabBar) => {
    const ul = createUl();
    const appendTab = (tab) => {
        const li = createLi();
        const link = createLink({href: '?page=' + tab.page, text: tab.title});
        li.appendChild(link);
        if (tab.active) {
            li.classList.add('active');
        }
        ul.appendChild(li);
    };
    tabBar.forEach(appendTab);
    return ul;
};

const renderFragment = async (fragmentName) => {
    return await fragmentLib.loadElementFromUrl(fragmentName)
};

const renderPageModel = async (pageModel) => {
    const {tabBars, fragment} = pageModel;
    const div = createDiv();
    const appendTabBar = (tabBar) => {
        const renderedTabBar = renderTabBar(tabBar);
        div.appendChild(renderedTabBar);
    };
    tabBars.forEach(appendTabBar);
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
