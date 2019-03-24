const createUrl = () => {
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

    return {
        parseUrl
    }
};
