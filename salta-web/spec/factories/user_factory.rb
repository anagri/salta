Factory.define :user do |u|
  u.sequence(:email) {|n| "user#{n}@email.com"}
  u.password 'abcd1234'
  u.password_confirmation {|u| u.password}
  u.first_name 'Contact'
  u.last_name 'User'
  u.sequence(:phone) {|n| "999999999#{n}"}
  u.role Role::CONTACT
end