ActionMailer::Base.smtp_settings = {
    :tls                  => true,
    :address              => "smtp.gmail.com",
    :port                 => "587",
    :domain               => "gmail.com",
    :authentication       => :plain,
    :user_name            => "spiker.app",
    :password             => "test!123",
    :enable_starttls_auto => true
}
