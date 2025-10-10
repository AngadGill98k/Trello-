import { Injectable, signal } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class Service {
    public isloggedin:boolean=false
    toggle(){
        this.isloggedin = true
    }
    async csrf_token() {
        fetch(`http://localhost:8080/csrf-token`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error(error);
            });
    }
    getCookie(name: string): string | null {
        const matches = document.cookie.match(
            new RegExp('(?:^|; )' + name.replace(/([.$?*|{}()[\]\\/+^])/g, '\\$1') + '=([^;]*)')
        );
        return matches ? decodeURIComponent(matches[1]) : null;
    }

    public csrf: string | null = null;

    get_csrf(): string | null {
        this.csrf = this.getCookie('XSRF-TOKEN');
        return this.csrf;
    }


    public access_token: string = ""
    set_access_token(token: string) {
        this.access_token = token
    }
    async refresh_token() {
        try {
            const res = await fetch(`http://localhost:8080/access`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include' // important for sending cookies
            });

            if (!res.ok) {
                // handle non-200 responses
                console.error('HTTP error', res.status);
                return;
            }

            // Only parse JSON if the response has content
            const text = await res.text();
            if (!text) {
                console.warn('Empty response from server');
                return;
            }

            const data = JSON.parse(text);
            console.log('Access token response:', data);
        } catch (error) {
            console.error('Fetch error:', error);
        }
    }
}