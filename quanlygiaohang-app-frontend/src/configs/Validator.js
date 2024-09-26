const usernameMinLength = 5;
const passwordMinLength = 6;
const cccdLength = 12;
const phoneLength = 10;

const validateUsername = (username) => {
    if (
        username === null ||
        username === '' ||
        username.length < usernameMinLength
    )
        return `Tên tài khoản dài ít nhất ${usernameMinLength} ký tự`;

    return null;
};


const validatePassword = (password) => {
    const alphanumericPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]+$/;
    if (
        password === null ||
        password === '' ||
        password.length < passwordMinLength || !alphanumericPattern.test(password)
    ) {

        return `Mật khẩu phải dài ít nhất ${passwordMinLength} ký tự và bao gồm chữ và số`;
    }

    return null;
};

const validateConfirmPassword = (p, cp) => {

    if (p === cp) return null;

    return `Mật khẩu không trùng nhau`;

};

const validatePhone = (phone) => {
    if (
        !isNaN(phone) && phone.trim() !== ''
        && phone.length === phoneLength
    ) return null;

    return `Số điện thoại không hợp lệ`;

};

const validateCCCD = (cccd) => {
   
    if (cccd === "" ||
        (!isNaN(cccd) && cccd.trim() !== ''
        && cccd.length === cccdLength)
    ) return null;

    return `Số căn cước không hợp lệ`;

};

export { validateUsername, validatePassword, validateCCCD, validatePhone, validateConfirmPassword };