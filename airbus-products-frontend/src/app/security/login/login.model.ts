
export enum loginFields {
    USERNAME = 'username',
    PASSWORD = 'password'
}

export interface TokenModel {
    accessToken: string;
    refreshToken: string;
    username: string;
    expires_in: number;
    refresh_expires_in: number;
    expires_at?: string;
    refresh_expires_at?: string;
}

export interface LoginForm {
    username: string;
    password: string;
}