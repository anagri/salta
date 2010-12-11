class User < ActiveRecord::Base
  acts_as_authentic
  has_many :memberships, :foreign_key => 'contact_id'
  has_many :groups, :through => :memberships

  def roles
    [role.to_sym]
  end

  def admin?
    role == Role::ADMIN
  end

  def contact?
    role == Role::CONTACT
  end
end