export class RegistrationDTO {
  constructor(
    public username: string,
    public password: string,
    public email: string,
    public role: string
  ) {}
}
