const urlLib = createUrlLib();
const fragmentLib = createFragmentLib();

const urlToPageName = (url) => {
    const parsed = urlLib.parseUrl(url);
    const target = 'page';
    const queryParts = parsed.queryParts;
    const defaultValue = 'a';
    const pageName = urlLib.lookupSingleValueFromQuery({target, queryParts, defaultValue});
    return pageName
};

const modelPromise = fragmentLib.loadJsonFromUrl('model.json');

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

const renderTabBar = (args) => {
    const {tabBar, index} = args;
    const ul = createUl();
    ul.classList.add('nav-' + index);
    const appendTab = (tab) => {
        const li = createLi();
        const link = createLink({href: '?page=' + tab.page, text: tab.title});
        li.appendChild(link);
        if (tab.selected) {
            li.classList.add('selected');
        }
        ul.appendChild(li);
    };
    tabBar.forEach(appendTab);
    return ul;
};

const renderFragment = async (fragmentName) => {
    return await fragmentLib.loadElementFromUrl('content/' + fragmentName)
};

const renderPageModel = async (pageModel) => {
    const {tabBars, fragment} = pageModel;
    const div = createDiv();
    let index = 0;
    const appendTabBar = (tabBar) => {
        index++;
        const renderedTabBar = renderTabBar({tabBar, index});
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
    const model = await modelPromise;
    const pageModel = model[pageName];
    return await renderPageModel(pageModel)
};

const init = async () => {
    document.body.appendChild(await render());
};

const promise = init();
