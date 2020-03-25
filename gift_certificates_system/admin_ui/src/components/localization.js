import LocalizedStrings from 'react-localization';
export const localized = new LocalizedStrings({
    en: {
        tooShort: 'Too short',
        onlyAdmins: 'Only admins can go there!',
        serviceUnavalable: 'Service unavailable now'
    },
    ru: {
        tooShort: 'Слишком коротко!',
        onlyAdmins: 'Только администратор может заходить!',
        serviceUnavalable: 'Сервис временно недоступен!'
    }
});