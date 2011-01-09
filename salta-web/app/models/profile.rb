class Profile < ActiveRecord::Base
  belongs_to :user
  has_many :memberships, :foreign_key => 'member_id', :dependent => :destroy
  has_many :groups, :through => :memberships
  validates_presence_of :first_name, :last_name, :email, :phone
  validates_format_of :phone, :with => /\d+/
  validates_length_of :phone, :is => 10
  validates_length_of :first_name, :maximum => 30
  validates_length_of :last_name, :maximum => 30
end