export const isEmpty = (v) => {
    if (!v) {
        return true;
    }
    if (Array.isArray(v)) {
        return !v.length; 
    }
    if (typeof v === 'object') {
        return !Object.keys(v).length; 
    }

    return false;
};

export const isNotEmpty = v => {
    return !isEmpty(v);
};
