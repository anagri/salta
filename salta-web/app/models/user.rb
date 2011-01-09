class User < ActiveRecord::Base
  acts_as_authentic
  has_many :profiles, :dependent => :destroy

  scope :contact, where(:role => Role::CONTACT)
  scope :admin, where(:role => Role::ADMIN)

  def role_symbols
    [role.to_sym]
  end

  def admin?
    role == Role::ADMIN
  end

  def contact?
    role == Role::CONTACT
  end

  def groups
    profiles.collect{|profile| profile.groups}.flatten
  end
end