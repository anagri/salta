class User < ActiveRecord::Base
  acts_as_authentic
  has_many :memberships, :foreign_key => 'contact_id'
  has_many :groups, :through => :memberships

  validates_presence_of :first_name, :last_name, :email, :phone
  validates_format_of :phone, :with => /\d+/
  validates_length_of :phone, :is => 10
  validates_length_of :first_name, :maximum => 30
  validates_length_of :last_name, :maximum => 30
  validates_length_of :phone, :maximum => 20
  validates_uniqueness_of :phone

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
end